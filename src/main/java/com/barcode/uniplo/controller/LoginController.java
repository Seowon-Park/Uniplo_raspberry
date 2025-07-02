package com.barcode.uniplo.controller;

import com.barcode.uniplo.domain.UserDto;
import com.barcode.uniplo.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("user_email") String email,
                        @RequestParam("user_password") String password,
                        HttpServletRequest request) {

        UserDto userDto = loginService.login(email, password);
        if (userDto != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("authUser", userDto);
            return "redirect:/";
        }

        // 로그인 실패 시 다시 로그인 폼으로 이동 (필요하면 메시지 추가 가능)
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
