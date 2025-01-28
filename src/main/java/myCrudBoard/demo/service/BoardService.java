package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

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
        Board board = boardDTO.toEntity();
        log.info("게시글 글 쓰기 성공 {}",board);
        boardRepository.save(board);
    }

    public void boardUpdate(BoardDTO boardDTO,Long id) {
        Board board = boardDTO.toEntity();
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
        boardRepository.deleteById(id);
    }
}
