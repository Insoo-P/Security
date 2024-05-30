package com.board.demo.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String id;
    private String pw;
    private String fullName;
    private String email;
    // Role Table에서 가져오기
    private Set<String> roles;
}