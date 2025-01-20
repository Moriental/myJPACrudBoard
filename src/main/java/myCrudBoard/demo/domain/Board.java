package myCrudBoard.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board extends baseEntity {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;
    private String content;
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="USER_ID")
    private User user;
}
