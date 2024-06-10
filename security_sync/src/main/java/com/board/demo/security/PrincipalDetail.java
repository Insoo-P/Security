package com.board.demo.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class PrincipalDetail implements UserDetails {
//    private User user;
//
//    public PrincipalDetail(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // 사용자의 권한을 반환
//        return user.getAuthorities();
//    }
//
//
//    @Override
//    public String getPassword() {
//        // 사용자의 비밀번호 반환
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        // 사용자의 아이디 반환
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // 계정이 만료되지 않았음을 반환
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // 계정이 잠기지 않았음을 반환
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // 자격 증명이 만료되지 않았음을 반환
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // 계정이 활성화되었음을 반환
//        return true;
//    }
//}