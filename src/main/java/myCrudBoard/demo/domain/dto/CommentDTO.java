package myCrudBoard.demo.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.Comment;
import myCrudBoard.demo.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    @NotBlank(message = "댓글 내용이 비어있으면 안됩니다.")
    private String content;

    private Long user; // User 엔티티
    private String username;

    private Long board;//board 엔티티

    private LocalDateTime createdAt;


    @Builder
    public CommentDTO(String content,String username,Long user,Long board,LocalDateTime created_at,LocalDateTime updated_at) {
        this.content = content;
        this.username = username;
        this.user = user;
        this.board = board;

    }

    public static CommentDTO fromEntity(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .user(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .board(comment.getBoard().getId())
                .build();
    }

    public Comment toEntity(User user, Board board) {
        Comment comment = new Comment();
        comment.setContent(this.content);
        comment.setUser(user); // 유저 엔티티에 아이디를 가져오는 건가 ?
        comment.setUsername(user.getUsername());
        comment.setBoard(board);
        return comment;
    }
}
