package com.example.demo.security;

import com.example.demo.storage.MemberStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DummyUserMapper {

    @Autowired
    private MemberStorage memberStorage;

    public Member getUserInfo(String id) {
        // Member user = memberStorage.getData(id);
        Member user = new Member("admin","123","USER");
        if (!Objects.isNull(user)) {
            return Member.builder()
                    .id(user.getId())
                    .pw(user.getPw())
                    .roles("USER")
                    .build();
        }
        return null;
    }
}
