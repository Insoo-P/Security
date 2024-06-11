package com.board.demo.security;

import com.board.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private MemberService memberService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;
        if (exception instanceof BadCredentialsException) {
            String id = request.getParameter("id");
            Member member = memberService.findMemberById(id);
            if(Objects.isNull(member)){
                errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
            }else{
                memberService.incrementFailedLoginAttempts(id);
                int failedCnt = memberService.retrieveFailedLoginAttempts(id);
                if(failedCnt > 4){
                    errorMessage = String.format("현재 %d번의 실패 시도로 계정이 잠겼습니다.", failedCnt);
                }else{
                    errorMessage = String.format("비밀번호를 확인해주세요. (현재 %d번의 실패 시도)", failedCnt);
                }
            }
        } else if (exception.getCause() instanceof LockedException) {
            errorMessage = exception.getMessage();
        } else if (exception.getCause() instanceof UsernameNotFoundException) {
            errorMessage = exception.getMessage();
        } else if (exception.getCause() instanceof EmptyResultDataAccessException) {
            errorMessage = "데이터베이스에서 결과를 찾을 수 없습니다.";
        } else {
            errorMessage = "알 수없는 오류입니다.";
        }

        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/member/view/login");
    }
}
