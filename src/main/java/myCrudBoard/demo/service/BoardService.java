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
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

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

    public void boardUpdate(BoardDTO boardDTO,Long id, User user) {
        Board board = boardDTO.toEntity(user);
        boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 하는 글이 없습니다."));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());

        log.info("게시글 업데이트 성공 {}",board);
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
