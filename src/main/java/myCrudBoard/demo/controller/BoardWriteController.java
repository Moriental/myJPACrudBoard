package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor

public class BoardWriteController {
    private final BoardService boardService;
    @GetMapping("/board_write")
    public String boardWrite(Model model) {
        model.addAttribute("boardDTO",BoardDTO.builder().build());
        return "board_write";
    }
    @PostMapping("/board_write")
    public String boardWriteProcess(@Valid @ModelAttribute BoardDTO boardDTO, BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("boardDTO",boardDTO);
            return "board_write";
        }
        boardService.boardWrite(boardDTO);
        return "redirect:/";
    }
}
