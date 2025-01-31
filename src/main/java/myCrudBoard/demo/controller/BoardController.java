package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myCrudBoard.demo.domain.Board;
import myCrudBoard.demo.domain.User;
import myCrudBoard.demo.domain.dto.BoardDTO;
import myCrudBoard.demo.domain.dto.CustomUserDetails;
import myCrudBoard.demo.repository.BoardRepository;
import myCrudBoard.demo.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String board(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        }
        else{
            log.info("현재 접속중인 유저가 없음 {}",username);
        }

        boardDTO = boardService.findById(id);
        log.info("게시판 작성자 : {}", boardDTO.getUserName());

        boolean isOwner = username!= null && boardDTO.getUserName().equals(username);

        model.addAttribute("boardDTO",boardDTO);
        model.addAttribute("isOwner",isOwner);

        return "board_detail";
    }

    @PostMapping("/board/{id}/delete")
    public String boardDelete(@PathVariable Long id, BoardDTO boardDTO) {
        boardService.boardDelete(boardDTO,id);
        return "redirect:/";
    }

    @GetMapping("/board/{id}/update")
    public String boardUpdate(@PathVariable("id") Long id,@ModelAttribute BoardDTO boardDTO, Model model) {
        boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO", boardDTO);
        return "board_update";
    }

    @Transactional
    @PostMapping("/board/{id}/update")
    public String boardUpdateProc(@PathVariable("id") Long id, @ModelAttribute BoardDTO boardDTO,User user) {
        boardService.boardUpdate(boardDTO,id);
        return "redirect:/board/" + id ; // 상세 페이지로 리다이렉트
    }
    @GetMapping("/search") //검색 바 만들기
    public String search(@RequestParam("keyword") String keyword,Model model){
        List<Board> boards = boardService.findByTitleContaining(keyword);
        model.addAttribute("boardList",boards);
        return "board";
    }
}
