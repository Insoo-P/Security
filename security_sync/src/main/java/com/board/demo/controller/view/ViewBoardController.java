package com.board.demo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/board")
public class ViewBoardController {

    @GetMapping("/normalBoardList")
    public String viewNormalBoardListPage() { return "board/normalBoardList"; }
}