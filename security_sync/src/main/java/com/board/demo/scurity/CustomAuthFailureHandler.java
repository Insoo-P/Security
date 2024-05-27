package com.board.demo.scurity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디와 비밀번호를 확인해주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다.";
        } else {
            errorMessage = "알 수없는 오류입니다.";
        }

//        request.getSession().setAttribute("exception", exception.getMessage());
//        setDefaultFailureUrl("/view/member/login?error=" + errorMessage);
//        super.onAuthenticationFailure(request, response, exception);

//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");

        // 실패 핸들러에서 모델에 에러 메시지 추가
        request.getSession().setAttribute("error", errorMessage);

        // 에러 응답을 직접 생성하지 않고, 기본적인 리다이렉트로 처리
        setDefaultFailureUrl("/view/member/login");
        super.onAuthenticationFailure(request, response, exception);

    }

}
