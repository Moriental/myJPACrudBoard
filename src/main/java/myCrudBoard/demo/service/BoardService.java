package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

}
