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

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(UserDTO userDTO) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        userDTO.setPassword(encodedPassword);
        userDTO.setRoleStatus(RoleStatus.USER.name());

        User user = userDTO.toEntity();
        System.out.println(userDTO);

        if(userRepository.existsByUsername(user.getUsername())){
            log.info("중복 닉네임은 가입 시도 {}",user.getUsername());
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        userRepository.save(user);
        log.info("가입 성공 {}",user.getUsername());
    }
}
