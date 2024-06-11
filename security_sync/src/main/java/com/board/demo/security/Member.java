package com.board.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String id;
    private String pw;
    private String fullName;
    private String email;
    private int loginAttempts;
    private boolean accountLocked;
    private Set<Role> roles;
}