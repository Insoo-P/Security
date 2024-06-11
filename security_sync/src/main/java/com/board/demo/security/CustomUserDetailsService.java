package com.board.demo.security;

import com.board.demo.member.repository.UserRepository;
import com.board.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Autowired
    public CustomUserDetailsService(MemberService memberService){
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberService.findMemberById(id);

        if (member == null) {
            throw new UsernameNotFoundException("");
        }

        if(memberService.checkIfAccountIsLocked(id)){
            throw new LockedException("계정이 잠겨 있습니다.");
        }

        String authoritiesString = member.getRoles().stream()
                .flatMap(role -> Arrays.stream(role.getRole().split(",")))
                .map(String::trim)
                .collect(Collectors.joining(","));

        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getId())
                .password(member.getPw())
                .roles(authoritiesString.split(","))
                .build();
    }

//    private Collection<? extends GrantedAuthority> authorities() {
//        return Arrays.asList(new SimpleGrantedAuthority("USER")); // 권한 세팅
//    }
}