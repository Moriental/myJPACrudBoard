package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.RoleStatus;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.UserDTO;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(UserDTO userDTO) {
        User user = new User();
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        System.out.println(user);

        user.updateUserDetails(
                userDTO.getUsername(),
                userDTO.getNickname(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                RoleStatus.USER
        );
        if(userRepository.existsByUsername(user.getUsername())){
            log.info("중복 닉네임은 가입 시도 {}",user.getUsername());
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        userRepository.save(user);
        log.info("가입 성공 {}",user.getUsername());
    }
}
