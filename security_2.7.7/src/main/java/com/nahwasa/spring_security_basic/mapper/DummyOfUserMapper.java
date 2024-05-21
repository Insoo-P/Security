package com.nahwasa.spring_security_basic.mapper;

import com.nahwasa.spring_security_basic.repository.Storage;
import com.nahwasa.spring_security_basic.config.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DummyOfUserMapper {

    @Autowired
    private Storage storage;

    public UserInfo getUserInfo(String insertedId) {
        UserInfo user = storage.getData(insertedId);

        if (!Objects.isNull(user)) {
            return UserInfo.builder()
                    .id(user.getId())
                    .pw(user.getPw())
                    .roles("USER")
                    .build();
        }
        return null;
    }
}



