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
    public Map<String, Object> test(@RequestBody Map<String, Object> paramMap ) {
        return paramMap;
    }

}
