package com.board.demo.cmmn.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error/view")
    public String viewErrorPage(HttpServletRequest request, HttpServletResponse response){
        int statusCode = response.getStatus();

        String errorStatus = (String) request.getAttribute("errorStatus");
        String msg = (String) request.getAttribute("msg");
        String nextPage = (String) request.getAttribute("nextPage");

        errorStatus = (errorStatus != null && !errorStatus.isBlank()) ? errorStatus : "";
        msg = (msg != null && !msg.isBlank()) ? msg : "잘못된 접근입니다.";
        nextPage = (nextPage != null && !nextPage.isBlank()) ? nextPage : "/";

        switch (statusCode){
            case 404:
                errorStatus = "404";
                msg = "페이지가 없습니다. 메인 페이지로 이동합니다.";
            case 405:
                errorStatus = "405";
                msg = "현재 접근할 권한이 없습니다. 메인 페이지로 이동합니다.";
        }

        request.setAttribute("errorStatus", errorStatus);
        request.setAttribute("msg", msg);
        request.setAttribute("nextPage", nextPage);

        return "error/error";
    }
}