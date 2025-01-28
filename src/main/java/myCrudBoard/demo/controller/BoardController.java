package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String board(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boardList",boardList);

        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        } else {
            model.addAttribute("user", null);
        }

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
    public String boardDetail(@PathVariable Long id, @ModelAttribute BoardDTO boardDTO, Model model) {
        boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO",boardDTO);
        return "board_detail";
    }

    @GetMapping("/board/{id}/update")
    public String boardUpdate(@PathVariable("id") Long id,@ModelAttribute BoardDTO boardDTO, Model model) {
        boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO", boardDTO);
        return "board_update";
    }

    @Transactional
    @PostMapping("/board/{id}/update")
    public String updateBoard(@PathVariable("id") Long id, @ModelAttribute BoardDTO boardDTO) {
        //boardService.update(id, boardDTO); // 업데이트 로직 구현 필요
        return "redirect:/board/" + id; // 상세 페이지로 리다이렉트
    }

    /*private static void getIdAuthentication() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iter =authorities.iterator();
        GrantedAuthority grantedAuthority = iter.next();
        String role =grantedAuthority.getAuthority();
    }*/ // 시큐리티 사용자 id 이름 가져오는 코드
}
