package com.board.demo.member.controller;

import com.board.demo.member.repository.UserRepository;
import com.board.demo.member.service.MemberService;
import com.board.demo.security.Member;
import com.board.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;


    @Autowired
    UserRepository userRepository;

    // 회원가입 처리
    @PostMapping("/api/signUp")
    public String addMember(Member member, Model model) {
        String message = "";
        if(!userRepository.existsById(member.getId())){
            int cnt = memberService.addMemberInfo(member);
            message = cnt > 0 ? "회원가입을 성공했습니다." : "회원가입이 실패했습니다.";
        } else {
            message = "회원 아이디가 존재합니다.";
        }
        model.addAttribute("message", message);
        return "member/login";
    }

    // 로그인 페이지 보기
    @GetMapping("/view/login")
    public String viewLoginPage(HttpSession session, Model model) {
        String error = (String) session.getAttribute("error");
        model.addAttribute("message", error);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken) ? "index" : "member/login";
    }

    // 회원가입 페이지 보기
    @GetMapping("/view/signUp")
    public String viewSignUpPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken) ? "index" : "member/signUp";
    }

    // 마이페이지 보기
    @GetMapping("/view/myPage")
    public String viewMyPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findMemberById(authentication.getName());
        if(!Objects.isNull(member)){
            model.addAttribute("message", "안녕하세요! " + member.getFullName() + " 님");
            model.addAttribute("member", member);
        }
        return "member/myPage";
    }

}
