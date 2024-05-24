package com.example.demo;

import com.example.demo.cmmn.ResponseVO;
import com.example.demo.security.Member;
import com.example.demo.storage.MemberStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    @Autowired
    MemberStorage memberStorage;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(name = "로그인", path= "/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> paramMap ) {
        return paramMap;
    }

    @PostMapping(name = "회원가입", path= "/signUp")
    @ResponseBody
    public ResponseVO signUp(@RequestBody Member paramMember) {
        paramMember.setRoles("ROLE");
        paramMember.setPw(passwordEncoder.encode(paramMember.getPw()));
        memberStorage.putData(paramMember.getId(), paramMember);
        return ResponseVO.saveOk();
    }

}
