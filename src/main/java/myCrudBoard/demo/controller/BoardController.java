package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.service.BoardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iter =authorities.iterator();
        GrantedAuthority grantedAuthority = iter.next();
        String role =grantedAuthority.getAuthority();

        List<Board> boardList = boardService.findAll();
        model.addAttribute("role",role);
        model.addAttribute("id",id);
        model.addAttribute("boardList",boardList);
        return "board";
    }
    @GetMapping("/board_write")
    public String boardWrite(Model model) {
        model.addAttribute("boardDTO", BoardDTO.builder().build());
        return "board_write";
    }
    @Transactional
    @PostMapping("/board_write")
    public String boardWriteProcess(@Valid @ModelAttribute BoardDTO boardDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("boardDTO",boardDTO);
            return "board_write";
        }
        boardService.boardWrite(boardDTO);
        return "redirect:/";
    }
    @GetMapping("/board/{id}")
    public String board(@PathVariable Long id,@ModelAttribute BoardDTO boardDTO, Model model) {
        boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO",boardDTO);
        System.out.println(boardDTO.getTitle());
        System.out.println(boardDTO.getContent());
        System.out.println(boardDTO.getViews());
        System.out.println(boardDTO.getCreatedAt());
        return "board_detail";
    }
}
