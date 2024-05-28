package com.board.demo.security;

import com.board.demo.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

        List<String> roles = member.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return User.builder()
                .username(member.getId())
                .password(member.getPw())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}