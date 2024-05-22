package com.example.demo.storage;

import com.example.demo.security.Member;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemberStorage{

    private Map<String, Member> dataMap = new HashMap<>();

    public void putData(String key, Member member) {
        dataMap.put(key, member);
    }

    public Member getData(String key) {
        return dataMap.get(key);
    }

    public void removeData(String key) {
        dataMap.remove(key);
    }
}
