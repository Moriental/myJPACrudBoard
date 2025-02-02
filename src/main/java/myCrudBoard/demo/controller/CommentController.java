package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.Comment;
import myCrudBoard.demo.domain.dto.CommentDTO;
import myCrudBoard.demo.domain.dto.CustomUserDetails;
import myCrudBoard.demo.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 생성 API
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO, BindingResult bindingResult, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        CommentDTO savedComment = commentService.createComment(commentDTO, userDetails.getUsername());
        return ResponseEntity.ok(savedComment);
    }

    //댓글 목록 조회 API
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<CommentDTO> commentList = commentService.getComments(boardId);
        return ResponseEntity.ok(commentList);
    }
}

