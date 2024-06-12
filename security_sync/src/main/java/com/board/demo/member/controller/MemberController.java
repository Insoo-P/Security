package com.board.demo.member.controller;

import com.board.demo.member.service.MemberService;
import com.board.demo.security.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
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
        boolean result = memberService.addMemberInfo(member);
        message = result ? "회원가입을 성공했습니다." : "회원가입이 실패했습니다.";
        model.addAttribute("message", message);
        return "member/login";
    }

    // 회원정보 수정
    @PostMapping("/api/edit")
    public String editMember(Member member, Model model) {
        String message = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member modifyBeforeMember = memberService.findMemberById(authentication.getName());
        boolean pwSame = passwordEncoder.matches(member.getPw(), modifyBeforeMember.getPw());
        if(!pwSame){
            message = "비밀번호가 틀립니다.";
            model.addAttribute("message", message);
            model.addAttribute("member", modifyBeforeMember);
            return "member/myPage";
        }
        // 수정 사항 확인
        boolean fullNameModifiedYn = modifyBeforeMember.getFullName().equals(member.getFullName());
        boolean emailModifiedYn = modifyBeforeMember.getEmail().equals(member.getEmail());

        if(!fullNameModifiedYn){
            modifyBeforeMember.setFullName(member.getFullName());
        }

        if(!emailModifiedYn){
            if(memberService.checkIfEmailExists(member.getEmail())){
                message = "이메일이 이미 등록되어 있습니다.";
                model.addAttribute("message", message);
                model.addAttribute("member", modifyBeforeMember);
                return "member/myPage";
            }else{
                modifyBeforeMember.setEmail(member.getEmail());
            }
        }

        if(fullNameModifiedYn && emailModifiedYn){
            message = "변경 사항이 없습니다.";
            model.addAttribute("message", message);
            model.addAttribute("member", modifyBeforeMember);
            return "member/myPage";
        }

        boolean result = memberService.modifyMemberInfo(modifyBeforeMember);
        message = result ? "회원정보 수정 완료되었습니다." : "회원정보 수정 실패했습니다.";
        Member modifyMember = memberService.findMemberById(authentication.getName());
        model.addAttribute("message", message);
        model.addAttribute("member", modifyMember);
        return "member/myPage";
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
