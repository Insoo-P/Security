package com.board.demo.member.service;

import com.board.demo.security.Member;
import com.board.demo.member.repository.UserRepository;
import com.board.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public int addMemberInfo(Member member){
        member.setPw(passwordEncoder.encode(member.getPw()));
        // 회원 저장
        int memberCnt = userRepository.saveMember(member);
        int roleCnt = 0;
        // 기본 권한 저장
        if(memberCnt > 0){
            Role defaultRole = new Role();
            defaultRole.setId(member.getId());
            defaultRole.setRole("USER");
            roleCnt = addMemberRole(defaultRole);
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
