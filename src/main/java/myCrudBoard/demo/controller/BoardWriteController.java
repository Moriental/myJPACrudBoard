package myCrudBoard.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardWriteController {
    @GetMapping("/board_write")
    public String boardWrite(){
        return "board_Write";
    }
}
