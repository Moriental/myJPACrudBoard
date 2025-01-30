package myCrudBoard.demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class User extends baseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String username;
    private String password;

    @Column(unique = true)
    private String nickname;

    private String email;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> boards =  new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments =  new ArrayList<>();
    
    //편의 메서드
    public void addBoard(Board board) {
        this.boards.add(board);
        board.setUser(this);  // Board의 user를 현재 User로 설정
    }

    // Comment 추가를 위한 편의 메서드
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);  // Comment의 user를 현재 User로 설정
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getEmail(), user.getEmail()) && getRoleStatus() == user.getRoleStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getNickname(), getEmail(), getRoleStatus());
    }
    public void updateUserDetails(String username, String nickname, String email, String password, RoleStatus roleStatus) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roleStatus = roleStatus;
    }
}
