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

    @NotBlank(message = "제목이 비어있으면 안됩니다.")
    private String title;
    @NotBlank(message = "내용이 비어있으면 안됩니다.")

    private String content;
    private int views;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public BoardDTO(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
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
