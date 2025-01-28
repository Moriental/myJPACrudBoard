package myCrudBoard.demo.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("로그아웃 처리 중");
    }
}
