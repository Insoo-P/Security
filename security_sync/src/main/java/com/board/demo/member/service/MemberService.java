package com.board.demo.member.service;

import com.board.demo.security.Member;
import com.board.demo.member.repository.UserRepository;
import com.board.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public int addMemberInfo(Member member){
        member.setPw(passwordEncoder.encode(member.getPw()));
        int memberCnt = userRepository.saveMember(member);
        int roleCnt = 0;
        // 기본 권한 저장
        if(memberCnt > 0){
            Role defaultRole = new Role();
            defaultRole.setId(member.getId());
            defaultRole.setRole("USER");
            roleCnt = addMemberRole(defaultRole);
            // 테스트
            Role adminRole = new Role();
            adminRole.setId(member.getId());
            adminRole.setRole("ADMIN");
            roleCnt = addMemberRole(adminRole);
        }

        return roleCnt;
    }

    public int addMemberRole(Role role){
        return userRepository.saveRole(role);
    }

    public Member findMemberById(String id){
        return userRepository.findById(id);
    }
}
