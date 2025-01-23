package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public List<Board> findAll(){
        return boardRepository.findAll();
    }
    public void boardWrite(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
    }
    public void boardUpdate(BoardDTO boardDTO,Long id) {
        Board board = boardDTO.toEntity();
        boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 하는 글이 없습니다."));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        boardRepository.save(board);
    }
    public void boardDelete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 하는 글이 없습니다.");
        }
        boardRepository.deleteById(id);
    }
}
