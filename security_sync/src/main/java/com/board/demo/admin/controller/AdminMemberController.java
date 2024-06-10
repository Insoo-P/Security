package com.board.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

    @GetMapping("/view/list")
    public String viewAdminMemberListPage(){
        return "admin/member/adminMemberList";
    }
}
