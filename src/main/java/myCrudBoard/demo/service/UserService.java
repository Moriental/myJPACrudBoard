package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.UserDTO;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateUser(Long id,UserDTO userDTO) {
        User user = userRepository.findById(id).get();

        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 하는 유저가 없습니다."));

        userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 하는 유저가 없습니다."));
    }
}
