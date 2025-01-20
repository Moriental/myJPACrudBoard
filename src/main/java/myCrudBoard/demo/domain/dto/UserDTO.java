package myCrudBoard.demo.domain.dto;

import lombok.*;
import myCrudBoard.demo.domain.RoleStatus;
import myCrudBoard.demo.domain.User;

@Getter
@Setter
//@NoArgsConstructor // 매개변수가 없는 클래스 생성자 (final 이 있을 경우 force = true)로 하여 0이나 null로 초기화함
public class UserDTO {
    private final Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String roleStatus;

    @Builder
    public UserDTO(Long id,String username, String nickname, String password, String email, String roleStatus) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.roleStatus = roleStatus;
    }
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
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
