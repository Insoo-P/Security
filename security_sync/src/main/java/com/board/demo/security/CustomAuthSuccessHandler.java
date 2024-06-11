package com.board.demo.security;

import com.board.demo.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;
    
    public CustomAuthSuccessHandler(MemberService memberService){
        this.memberService = memberService;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String id = request.getParameter("id");
        // 계정 잠금 해제 및 로그인 실패 횟수 초기화
        memberService.resetFailedLoginAttemptsAndUnlockAccount(id);
        response.sendRedirect("/member/view/myPage");
    }
}
