package myCrudBoard.demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class User extends baseEntity{
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    private String provider;
    private String provider_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getEmail(), user.getEmail()) && getRoleStatus() == user.getRoleStatus() && Objects.equals(getProvider(), user.getProvider()) && Objects.equals(getProvider_id(), user.getProvider_id()) && Objects.equals(getCreated_at(), user.getCreated_at()) && Objects.equals(getUpdated_at(), user.getUpdated_at());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getNickname(), getEmail(), getRoleStatus(), getProvider(), getProvider_id(), getCreated_at(), getUpdated_at());
    }
}
