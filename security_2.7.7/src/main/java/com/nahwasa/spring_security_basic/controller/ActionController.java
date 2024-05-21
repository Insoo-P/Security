package com.nahwasa.spring_security_basic.controller;

import com.nahwasa.spring_security_basic.repository.Storage;
import com.nahwasa.spring_security_basic.config.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private Storage storage;

    @PostMapping("/signUp")
    public String viewSignUpPage(@RequestParam("id") String id, @RequestParam("pw") String pw) {
        storage.putData(id, new UserInfo(id, pw, "USER"));
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String viewLoginSuccessPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userId", user.getUsername());
        model.addAttribute("userRoles", user.getAuthorities());
        return "loginSuccess";
    }
}
