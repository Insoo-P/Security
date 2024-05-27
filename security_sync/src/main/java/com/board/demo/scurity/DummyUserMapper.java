package com.board.demo.scurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DummyUserMapper {

    @Autowired
    private MemberStorage memberStorage;

    public Member getUserInfo(String id) {
        Member user = memberStorage.getData(id);
        if (!Objects.isNull(user)) {
            return Member.builder()
                    .id(user.getId())
                    .pw(user.getPw())
                    .roles(user.getRoles())
                    .build();
        }
        return null;
    }
}