package com.board.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        String referer = (String)request.getHeader("REFERER");
        if (requestUri.contains("/premium/")) {
            request.setAttribute("msg", "우수회원 유저만 접근 가능합니다.");
            request.setAttribute("nextPage", referer);
            request.getRequestDispatcher("/error/view").forward(request, response);
        } else if(requestUri.contains("/admin/")){
            request.setAttribute("msg", "운영자만 접근 가능합니다.");
            request.setAttribute("nextPage", referer);
            request.getRequestDispatcher("/error/view").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}