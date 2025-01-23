package myCrudBoard.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myCrudBoard.demo.domain.RoleStatus;
import myCrudBoard.demo.domain.dto.UserDTO;
import myCrudBoard.demo.service.JoinService;
import myCrudBoard.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final JoinService joinService;

    @GetMapping("/register")
    public String join(Model model) {
        model.addAttribute("userDTO",UserDTO.builder().build());
        return "register";
    }

    @PostMapping("/register")
    public String joinProcess(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult , Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("userDTO",userDTO);
            return "register";
        }
        joinService.createUser(userDTO);

        return "redirect:/";
    }
}
