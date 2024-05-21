package com.nahwasa.spring_security_basic.repository;

import com.nahwasa.spring_security_basic.config.UserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Storage {
    private Map<String, UserInfo> dataMap = new HashMap<>();

    // 데이터 추가
    public void putData(String key, UserInfo value) {
        dataMap.put(key, value);
    }

    // 데이터 조회
    public UserInfo getData(String key) {
        return dataMap.get(key);
    }

    // 데이터 삭제
    public void removeData(String key) {
        dataMap.remove(key);
    }
}