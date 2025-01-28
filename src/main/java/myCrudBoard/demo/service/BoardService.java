package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.domain.dto.CustomUserDetails;
import myCrudBoard.demo.repository.BoardRepository;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public BoardDTO findById(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id: " + id));
        log.info("게시판 번호 :  {}",id);
        return BoardDTO.fromEntity(board);
    }
    public BoardDTO findByIdWithUser(Long id){
        Board board = boardRepository.findByIdWithUser(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id :" + id));
        return BoardDTO.fromEntity(board);
    }
    @Transactional
    public void boardWrite(BoardDTO boardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        }
        User user = userRepository.findByUsername(username);
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
        // 현재 인증된 사용자 정보 가져오기
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
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }

        // 게시물 존재 여부 확인
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // 게시물 작성자와 현재 사용자 비교
        if (!board.getUser().getUsername().equals(username)) {
            throw new RuntimeException("게시물을 수정할 권한이 없습니다."); // 권한 에러 발생
        }

        // 게시물 수정
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());

        log.info("게시글 업데이트 성공 {}", board);

        // 변경사항 저장
        boardRepository.save(board);
    }

    public void boardDelete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 하는 글이 없습니다.");
        }
        log.info("게시글 삭제 성공 {}");

        boardRepository.deleteById(id);
    }
}
