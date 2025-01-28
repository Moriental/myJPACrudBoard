package myCrudBoard.demo.controller;

import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    @GetMapping("/login")
    public String Login(Model model) {
        return "login";
    }
}
