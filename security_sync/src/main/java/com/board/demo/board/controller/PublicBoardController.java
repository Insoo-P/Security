package com.board.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicBoardController {

    @GetMapping("/board/view/list")
    public String viewPublicBoardListPage() { return "board/public/publicBoardList"; }

    @GetMapping("/upBoard/view/list")
    public String viewPublicUpBoardListPage() { return "board/public/publicUpBoardList"; }
}