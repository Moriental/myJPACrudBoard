package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

}
