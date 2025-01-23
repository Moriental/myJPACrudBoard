package myCrudBoard.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myCrudBoard.demo.domain.Comment;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private String content;

    @Builder
    public CommentDTO(String content) {
        this.content =content;
    }

    public static CommentDTO fromEntity(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .build();
    }
    
    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }
}
