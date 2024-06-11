package com.board.demo.member.controller;

import com.board.demo.member.repository.UserRepository;
import com.board.demo.member.service.MemberService;
import com.board.demo.security.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    // 회원가입 로직 처리
    @PostMapping("/api/signUp")
    public String registerMember(Member member, Model model) {
        String message = "";
        if(memberService.checkIfIdExists(member.getId()) && memberService.checkIfEmailExists(member.getEmail())){
            message = "회원 아이디와 이메일이 모두 이미 등록되어 있습니다.";
            model.addAttribute("message", message);
            model.addAttribute("member", member);
            return "member/signUp";
        }
        else if(memberService.checkIfIdExists(member.getId())){
            message = "회원 아이디가 이미 존재합니다.";
            model.addAttribute("message", message);
            model.addAttribute("member", member);
            return "member/signUp";
        }
        else if(memberService.checkIfEmailExists(member.getEmail())){
            message = "이메일이 이미 등록되어 있습니다.";
            model.addAttribute("message", message);
            model.addAttribute("member", member);
            return "member/signUp";
        }
        else {
            boolean result = memberService.addMemberInfo(member);
            message = result ? "회원가입을 성공했습니다." : "회원가입이 실패했습니다.";
            model.addAttribute("message", message);
            return "member/login";
        }
    }

    // 로그인 페이지 보기
    @GetMapping("/view/login")
    public String viewLoginPage(HttpSession session, Model model) {
        String error = (String) session.getAttribute("error");
        if (error != null) {
            model.addAttribute("message", error);
            // 오류 메시지를 사용한 후에는 세션에서 삭제
            session.removeAttribute("error");
        }
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
