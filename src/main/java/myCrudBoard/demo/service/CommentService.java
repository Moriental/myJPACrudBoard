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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public void createComment(CommentDTO commentDTO, User user, Board board) {
        Comment comment = commentDTO.toEntity(user,board);
        comment.setContent(commentDTO.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(CommentDTO commentDTO,Long id,User user, Board board) {
        Comment comment = commentDTO.toEntity(user,board);
        commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        commentRepository.deleteById(id);
    }

    public void updateComment(CommentDTO commentDTO, Long id,User user, Board board){
        Comment comment = commentDTO.toEntity(user,board);
        commentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("댓글이 존재하지 않습니다."));
    }
}
