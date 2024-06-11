package com.board.demo.member.service;

import com.board.demo.security.Member;
import com.board.demo.security.Role;

public interface MemberService {
    int addMemberInfo(Member member);
    int addMemberRole(Role role);
    Member findMemberById(String id);
    boolean incrementFailedLoginAttempts(String id);
    int retrieveFailedLoginAttempts(String id);
    boolean lockAccount(String id);
    boolean checkIfAccountIsLocked(String id);
    boolean resetFailedLoginAttemptsAndUnlockAccount(String id);
}