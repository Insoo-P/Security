package com.board.demo.cmmn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewMainController {

    @GetMapping("")
    public String viewIndexPage() { return "index"; }

}