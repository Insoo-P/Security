package com.board.demo.controller.view;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view/member")
public class ViewMemberController {


    @GetMapping("/login")
    public String viewLoginPage(HttpSession session,
                                Model model) {
        //    @ModelAttribute("error") String error,
        String error = (String) session.getAttribute("error");
        model.addAttribute("error", error);

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