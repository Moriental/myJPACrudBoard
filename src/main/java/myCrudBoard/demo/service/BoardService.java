package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public void boardWrite(BoardDTO boardDTO) {
        Board board = new Board();
        board.updateBoardDetails(boardDTO.getTitle(),boardDTO.getContent(),boardDTO.getViews());
        log.info("게시글 글 쓰기 성공 {}",board);
        boardRepository.save(board);
    }

    public void boardUpdate(BoardDTO boardDTO,Long id) {
        Board board = boardDTO.toEntity();
        boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 하는 글이 없습니다."));
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
    }
    public void boardDelete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 하는 글이 없습니다.");
        }
        boardRepository.deleteById(id);
    }
}
