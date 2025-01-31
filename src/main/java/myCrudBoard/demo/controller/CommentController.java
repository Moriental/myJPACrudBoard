package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.Comment;
import myCrudBoard.demo.domain.dto.CommentDTO;
import myCrudBoard.demo.domain.dto.CustomUserDetails;
import myCrudBoard.demo.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentDTO commentDTO, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return null;
    }
}

