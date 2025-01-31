package myCrudBoard.demo.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    @NotBlank(message = "제목이 비어있으면 안됩니다.")
    private String title;
    @NotBlank(message = "내용이 비어있으면 안됩니다.")
    private String content;

    private int views;

    private String createdAt;
    private String updatedAt;

    private Long user;
    private String userName; // 작성자 이름

    @Builder
    public BoardDTO(Long id, String title, String content, int views,String createdAt, String updatedAt, String userName,Long user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.userName = userName;
    }

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .views(board.getViewCount())
                .user((board.getUser().getId()))
                .userName(board.getUser().getUsername())
                .build();
    }

    public Board toEntity(User user) {
        Board board = new Board();
        board.setId(this.id);
        board.setContent(this.getContent());
        board.setTitle(this.getTitle());
        board.setViewCount(this.getViews());
        board.setUser(user);
        board.setUsername(user.getUsername());
        return board;
    }
}
