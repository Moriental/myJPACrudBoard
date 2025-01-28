package myCrudBoard.demo.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myCrudBoard.demo.domain.Board;

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

    @Builder
    public BoardDTO(Long id, String title, String content, int views,String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .views(board.getViewCount())
                .build();
    }
    public Board toEntity() {
        Board board = new Board();
        board.setContent(this.getContent());
        board.setTitle(this.getTitle());
        board.setViewCount(this.getViews());
        return board;
    }
}
