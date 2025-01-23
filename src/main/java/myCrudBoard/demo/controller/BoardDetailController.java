package myCrudBoard.demo.controller;

import myCrudBoard.demo.domain.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardDetailController {
    @GetMapping("/board_detail")
    public String board_post(){
        return "board_deatil";
    }

}
