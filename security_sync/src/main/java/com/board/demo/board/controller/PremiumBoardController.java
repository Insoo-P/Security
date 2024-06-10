package com.board.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/premium")
public class PremiumBoardController {

    @GetMapping("/board/view/list")
    public String viewPublicBoardListPage() { return "board/premium/premiumBoardList"; }
}