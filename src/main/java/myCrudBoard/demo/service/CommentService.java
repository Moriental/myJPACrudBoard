package myCrudBoard.demo.service;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
}
