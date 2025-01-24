package myCrudBoard.demo.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import myCrudBoard.demo.domain.RoleStatus;
import myCrudBoard.demo.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 매개변수가 없는 클래스 생성자 (final 이 있을 경우 force = true)로 하여 0이나 null로 초기화함
public class UserDTO {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    private LocalDateTime created_at;

    private String roleStatus;

    @Builder
    public UserDTO(String username, String nickname, String password, String email, String roleStatus) {
        this.username = username; //이름
        this.nickname = nickname; //닉네임
        this.password = password; //비밀번호
        this.email = email; //이메일
        this.roleStatus = roleStatus;
        //this.created_at = (created_at == null) ? LocalDateTime.now() : created_at;
    }
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .roleStatus(String.valueOf(user.getRoleStatus()))
                .build();
    }
    public User toEntity(){
        User user = new User();
        user.setUsername(this.getUsername());
        user.setNickname(this.getNickname());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setRoleStatus(RoleStatus.valueOf(this.getRoleStatus()));
        return user;
    }


    //    public static UserDTO toDto(User user){
//        UserDTO dto = new UserDTO();
//        dto.setUsername(user.getUsername());
//        dto.setPassword(user.getPassword());
//        dto.setNickname(user.getNickname());
//        dto.setEmail(user.getEmail());
//        dto.setRoleStatus(user.getRoleStatus().toString());
//        return dto;
//    }
//    public static User toEntity(UserDTO dto){
//        User user = new User();
//        user.setUsername(dto.getUsername());
//        user.setPassword(dto.getPassword());
//        user.setNickname(dto.getNickname());
//        user.setEmail(dto.getEmail());
//        user.setRoleStatus(RoleStatus.valueOf(dto.getRoleStatus()));
//        return user;
//    }

}
