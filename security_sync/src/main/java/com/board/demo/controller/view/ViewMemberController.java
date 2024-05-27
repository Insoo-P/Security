package com.board.demo.controller.view;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/member")
public class ViewMemberController {

    @GetMapping("/login")
    public String viewLoginPage() {
        String viewUrl = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        viewUrl = authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken) ? "index" : "member/login";
        return viewUrl;
    }

    @GetMapping("/signUp")
    public String viewSignUpPage() {
        String viewUrl = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        viewUrl = authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken) ? "index" : "member/signUp";
        return viewUrl;
    }

    @GetMapping("/myPage")
    public String viewMyPage() { return "member/myPage"; }

}