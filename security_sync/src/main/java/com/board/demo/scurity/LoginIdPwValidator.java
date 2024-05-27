package com.board.demo.scurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginIdPwValidator implements UserDetailsService {

    @Autowired
    private DummyUserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member user = mapper.getUserInfo(id);

        if (user == null) {
            return null;
        }

        return User.builder()
                .username(user.getId())
                .password(user.getPw())
                .roles(user.getRoles())
                .build();
    }
}