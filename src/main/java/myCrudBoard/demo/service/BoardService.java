package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.domain.dto.CustomUserDetails;
import myCrudBoard.demo.repository.BoardRepository;
import myCrudBoard.demo.repository.CommentRepository;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> findByTitleContaining(String keyword,Pageable pageable){
        return boardRepository.findByTitleContaining(keyword,pageable);
    }

    @Transactional
    public BoardDTO findById(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            board.increaseViewCount();
            log.info("조회수 증가 {}",board.getViewCount());
        }
        else{
            log.info("현재 접속중인 유저가 없음 {}",username);
        }

        boardRepository.save(board);
        log.info("게시판 번호 : {}",id);

        return BoardDTO.fromEntity(board);
    }

    @Transactional
    public void boardWrite(BoardDTO boardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        if(user == null){
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }
        //현재 로그인한 세션 계정의 유저네임을 레포지토리에서 찾아서 게시글 작성

        Board board = boardDTO.toEntity(user);
        log.info("게시글 글 쓰기 성공 {}",board);
        log.info("게시글 작성한 사람 {}",user.getUsername());
        boardRepository.save(board);
    }

    @Transactional
    public void boardUpdate(BoardDTO boardDTO, Long id) {
        Result result = getResult(id);

        // 게시물 작성자와 현재 사용자 비교
        if (!result.board.getUser().getUsername().equals(result.username())) {
            throw new RuntimeException("게시물을 수정할 권한이 없습니다."); // 권한 에러 발생
        }

        // 게시물 수정
        result.board.setTitle(boardDTO.getTitle());
        result.board.setContent(boardDTO.getContent());

        log.info("게시글 업데이트 성공 {}", result.board);

        // 변경사항 저장
        boardRepository.save(result.board);
    }
    @Transactional
    public void boardDelete(BoardDTO boardDTOm,Long id) {
        Result result = getResult(id);

        // 게시물 작성자와 현재 사용자 비교
        if (!result.board().getUser().getUsername().equals(result.username())) {
            throw new RuntimeException("게시물을 삭제할 권한이 없습니다."); // 권한 에러 발생
        }

        log.info("삭제할려는 게시물 번호 : {}", result.board().getId());

        boardRepository.deleteById(id);

        log.info("게시글 삭제 성공");
    }

    private Result getResult(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        // 인증된 사용자 정보 확인
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        }

        if (username == null) {
            throw new RuntimeException("인증 정보가 없습니다.");
        }

        // 데이터베이스에서 현재 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        Result result = new Result(username, board);
        return result;
    }

    private record Result(String username, Board board) {
    }
}
