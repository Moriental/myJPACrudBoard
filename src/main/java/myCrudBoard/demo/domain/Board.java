package myCrudBoard.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Board extends baseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;
    @Column(length = 550)
    private String content;
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="USER_ID")
    private User user;

    private String username;

    public void setUser(User user) {
        this.user = user;
        user.getBoards().add(this);  // User의 boards 리스트에 현재 Board를 추가
    }
    public void increaseViewCount(){
        this.viewCount++;
    }
}
