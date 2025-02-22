package myCrudBoard.demo.config;

import myCrudBoard.demo.handler.CustomAuthenticationSuccessHandler;
import myCrudBoard.demo.handler.CustomLogoutHandler;
import myCrudBoard.demo.handler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

@Controller
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc","/search", "/register", "/board/{id}").permitAll() // 수정된 부분
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated());
        // 로그인 설정
        http
                .formLogin((auth) -> auth
                        .loginPage("/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/loginProc") // 로그인 처리 URL
                        .permitAll()
                        .defaultSuccessUrl("/",true)
                        .successHandler(customAuthenticationSuccessHandler));
        //로그 아웃
        http
                .logout(auth -> auth
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .addLogoutHandler(new CustomLogoutHandler())
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        // 세션 관리
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));
        http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/loginProc","register"));

        return http.build();
    }
}
