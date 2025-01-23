package myCrudBoard.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myCrudBoard.demo.domain.Board;

@Getter
@Setter
public class BoardDTO {
    private String title;
    private String content;
    private int views;

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
