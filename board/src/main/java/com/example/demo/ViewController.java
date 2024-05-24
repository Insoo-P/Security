package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("")
    public String viewIndexPage() { return "index"; }

    @GetMapping("view/login")
    public String viewLoginPage() { return "login"; }

    @GetMapping("view/signUp")
    public String viewSignUpPage() {
        return "signUp";
    }

    @GetMapping("view/boardList")
    public String viewBoardListPage() {
        return "boardList";
    }
}
