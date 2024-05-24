package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    @PostMapping(name = "로그인", path= "/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> paramMap ) {
        return paramMap;
    }

    @PostMapping(name = "회원가입", path= "/signUp")
    @ResponseBody
    public Map<String, Object> signUp(@RequestBody Map<String, Object> paramMap ) {

        return paramMap;
    }

}
