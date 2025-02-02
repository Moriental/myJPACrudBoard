package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.Comment;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.CommentDTO;
import myCrudBoard.demo.repository.BoardRepository;
import myCrudBoard.demo.repository.CommentRepository;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentDTO createComment(CommentDTO commentDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Board board = boardRepository.findById(commentDTO.getBoard())
                .orElseThrow(() -> new IllegalArgumentException(("해당 게시글이 존재하지 않습니다.")));

        commentDTO.setUser(user.getId());
        commentDTO.setUser(board.getId());

        Comment comment = commentDTO.toEntity(user,board);
        Comment savedComment = commentRepository.save(comment);

        return CommentDTO.fromEntity(savedComment);
    }
    public List<CommentDTO> getComments(Long boardID){
        List<Comment> comments = commentRepository.findByBoardId(boardID);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(Comment comment : comments) {
            CommentDTO dto = CommentDTO.fromEntity(comment);
            commentDTOList.add(dto);
        }
        return commentDTOList;
    }
}
