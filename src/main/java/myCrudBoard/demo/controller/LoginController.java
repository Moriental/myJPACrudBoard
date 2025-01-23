package myCrudBoard.demo.controller;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.dto.UserDTO;
import myCrudBoard.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @PostMapping("/login")
    public String LoginProcess(Model model, @RequestParam("username") String username, @RequestParam("password") String password, UserDTO userDTO){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        return "login";
    }
}
