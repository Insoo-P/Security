package com.nahwasa.spring_security_basic.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String viewSignUpPage() {
        return "signUp";
    }

    @GetMapping("/loginSuccess")
    public String viewLoginSuccessPage(@AuthenticationPrincipal User user, Model model) {
        String viewName = "loginSuccess";
        if(!Objects.isNull(user)){
            model.addAttribute("userId", user.getUsername());
            model.addAttribute("userRoles", user.getAuthorities());
        }else{
            viewName = "login";
        }
        return viewName;
    }
}
