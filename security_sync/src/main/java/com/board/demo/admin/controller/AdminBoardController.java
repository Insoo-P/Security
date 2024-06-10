package com.board.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

    @GetMapping("/view/list")
    public String viewAdminBoardListPage() {
        return "admin/board/adminBoardList";
    }
}
