package com.board.demo.member.repository;

import com.board.demo.security.Member;
import com.board.demo.security.Role;

public interface UserRepository {

    // 유저가 존재하는지 여부를 확인하는 메서드
    public boolean existsById(String id);

    // 유저 정보 찾는 메서드 (MEMBER, ROLES 포함)
    public Member findById(String id);

    // 유저 정보 업데이트하는 메서드
    public int updateMember(Member member);

    // 유저 정보 저장하는 메서드
    public int saveMember(Member member);

    // 유저 권한 저장하는 메서드
    public int saveRole(Role role);

    // 유저 권한 수정하는 메서드
    public int updateRole(Role role);

    // 유저 권한이 존재하는지 여부를 확인하는 메서드
    public boolean existsByRole(String id, String role);

}
