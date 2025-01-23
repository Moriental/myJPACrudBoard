package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.Comment;
import myCrudBoard.demo.domain.dto.CommentDTO;
import myCrudBoard.demo.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComment(){
        return commentRepository.findAll();
    }

    public void createComment(CommentDTO commentDTO) {
        Comment comment = commentDTO.toEntity();
        comment.setContent(commentDTO.getContent());
        commentRepository.save(comment);
    }
    public void deleteComment(CommentDTO commentDTO,Long id) {
        Comment comment = commentDTO.toEntity();
        commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        commentRepository.deleteById(id);
    }
    public void updateComment(CommentDTO commentDTO, Long id){
        Comment comment = commentDTO.toEntity();
        commentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("댓글이 존재하지 않습니다."));
    }
}
