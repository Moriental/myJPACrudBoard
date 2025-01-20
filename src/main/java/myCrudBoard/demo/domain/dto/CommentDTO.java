package myCrudBoard.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myCrudBoard.demo.domain.Comment;

@Getter
@Setter
public class CommentDTO {
    private final Long id; //id의 값은 변하면 안되기때문에 불변객체로 선언
    private String content;

    @Builder
    public CommentDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static CommentDTO fromEntity(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
    
    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }


}
