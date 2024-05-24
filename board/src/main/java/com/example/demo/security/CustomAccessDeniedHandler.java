package com.example.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (request.getRequestURI().equals("/view/login")) {
            response.sendRedirect("/"); // 인증된 사용자가 /view/login 접근 시 홈으로 리다이렉트
        } else {
            response.sendRedirect("/access-denied"); // 다른 접근 거부 시 처리 페이지로 리다이렉트
        }
    }
}
