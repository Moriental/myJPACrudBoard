package myCrudBoard.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@Slf4j
public class MyPageController {
    @GetMapping("/mypage")
    public String myPage(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iter =authorities.iterator();
        GrantedAuthority grantedAuthority = iter.next();
        String role =grantedAuthority.getAuthority();

        model.addAttribute("role",role);
        model.addAttribute("id",id);

        return "myPage";
    }
}
