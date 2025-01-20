package myCrudBoard.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myCrudBoard.demo.domain.Board;

@Getter
@Setter
public class BoardDTO {
    private final Long id;
    private String title;
    private String content;

    @Builder
    public BoardDTO(Long id, String title, String content, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
    public Board toEntity() {
        Board board = new Board();
        board.setContent(this.getContent());
        board.setTitle(this.getTitle());
        return board;
    }

}
