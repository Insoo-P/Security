package com.board.demo.member.service;

import com.board.demo.security.Member;
import com.board.demo.member.repository.UserRepository;
import com.board.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public int addMemberInfo(Member member){
        member.setPw(passwordEncoder.encode(member.getPw()));
        Role role = new Role("id", member.getRoles().toString());
        addMemberRole(role);
        return userRepository.saveMember(member);
    }

    public int addMemberRole(Role role){
        return userRepository.saveRole(role);
    }

    public Member findMemberById(String id){
        return userRepository.findById(id);
    }
}
