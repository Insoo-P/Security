package com.board.demo.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//PremiumBoardController
@RequestMapping("/public/board")
public class PublicBoardController {

    @GetMapping("/view/list")
    public String viewPublicBoardListPage() { return "board/public/publicBoardList"; }

}