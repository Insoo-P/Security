package com.board.demo.member.service;

import com.board.demo.security.Member;
import com.board.demo.security.Role;

public interface MemberService {

    // 유저가 존재하는지 여부를 확인
    boolean checkIfIdExists(String id);

    // Email이 존재하는지 여부를 확인
    boolean checkIfEmailExists(String email);

    // 유저 정보 조회 (MEMBER, ROLES 포함)
    Member findMemberById(String id);

    // 유저 정보 저장 (MEMBER, ROLES 포함)
    boolean addMemberInfo(Member member);

    // 유저 권한 저장
    int addMemberRole(Role role);


    // 로그인 실패 횟수 증가
    boolean incrementFailedLoginAttempts(String id);

    // 로그인 실패 횟수 조회
    int retrieveFailedLoginAttempts(String id);

    // 계정 잠금
    boolean lockAccount(String id);

    // 계정 잠금 여부 조회
    boolean checkIfAccountIsLocked(String id);

    // 계정 잠금 해제 및 로그인 실패 횟수 초기화
    boolean resetFailedLoginAttemptsAndUnlockAccount(String id);
}