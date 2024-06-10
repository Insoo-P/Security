package com.board.demo.security;

import com.board.demo.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = userRepository.findById(id);

        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
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

    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER")); // 권한 세팅
    }
}