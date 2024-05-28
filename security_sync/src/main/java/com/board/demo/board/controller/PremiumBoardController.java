package com.board.demo.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/premium/board")
public class PremiumBoardController {

    @GetMapping("/view/list")
    public String viewPublicBoardListPage() { return "board/premium/premiumBoardList"; }
}