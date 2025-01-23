package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.UserDTO;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        userRepository.save(user);
    }
    public void updateUser(Long id,UserDTO userDTO) {
        User user = userDTO.toEntity();
        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 하는 유저가 없습니다."));

        user.setUsername(userDTO.getUsername());
        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoleStatus(userDTO.toEntity().getRoleStatus());
        userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 하는 유저가 없습니다."));
    }
}
