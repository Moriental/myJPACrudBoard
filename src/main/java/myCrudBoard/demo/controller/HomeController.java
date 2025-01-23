package myCrudBoard.demo.controller;

import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    private final BoardService boardService;

    public HomeController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boardList",boardList);
        return "board";
    }
}
