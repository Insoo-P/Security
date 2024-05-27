package com.board.demo.controller.api;

import com.board.demo.scurity.Member;
import com.board.demo.scurity.MemberStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class ApiMemberController {

    @Autowired
    private MemberStorage memberStorage;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(name="회원가입", path="/signUp")
    public String viewSignUpPage(Member member) {
        member.setPw(passwordEncoder.encode(member.getPw()));
        memberStorage.putData(member.getId(), member);
        return "member/login";
    }
}
