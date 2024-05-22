package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("")
    public String viewIndexPage() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
//            return "board/boardList.html";
//        }
        return "index.html";
    }

    @GetMapping("view/login")
    public String viewLoginPage() {
        return "login.html";
    }

    @GetMapping("view/signUp")
    public String viewSignUpPage() {
        return "signUp.html";
    }

    @GetMapping("view/boardList")
    public String viewBoardListPage() {
        return "boardList.html";
    }
}
