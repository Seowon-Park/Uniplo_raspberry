package com.barcode.uniplo.controller;

import com.barcode.uniplo.domain.UserDto;
import com.barcode.uniplo.service.MailService;
import com.barcode.uniplo.service.SignupService;
import com.barcode.uniplo.validator.SignUpValidator;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private MailService mailService;

    @InitBinder
    public void toUser(WebDataBinder binder) {
        binder.addValidators(new SignUpValidator());
        List<Validator> validatorList = binder.getValidators();
        System.out.println("validatorList = " + validatorList);
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String addUser(@Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            // 유효성 검사 실패 시 다시 회원가입 폼으로
            return "login/signup";
        }
        if (signupService.addUser(userDto)) {
            return "login/signup-success";
        }
        return "login/signup";
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam("user_email") String user_email) {
        boolean exists = signupService.countEmail(user_email);
        return exists ? ResponseEntity.ok("DUPLICATE") : ResponseEntity.ok("OK");
    }

    @GetMapping("/send-auth-code")
    public ResponseEntity<String> sendAuthCode(@RequestParam("user_email") String user_email, HttpSession session) {
        String authCode = mailService.sendVerificationEmail(user_email);

        if (authCode == null) {
            return ResponseEntity.ok("FAIL");
        }

        session.setAttribute("authCode", authCode);
        session.setAttribute("authEmail", user_email);
        session.setMaxInactiveInterval(120);

        return ResponseEntity.ok("OK");
    }

    @PostMapping("/verify-auth-code")
    public ResponseEntity<String> verifyAuthCode(@RequestParam("inputCode") String inputCode, HttpSession session) {
        String sessionCode = (String) session.getAttribute("authCode");
        if (sessionCode == null) return ResponseEntity.ok("EXPIRED");

        if (!sessionCode.equals(inputCode)) return ResponseEntity.ok("INVALID");

        session.removeAttribute("authCode");
        return ResponseEntity.ok("VERIFIED");
    }
}
