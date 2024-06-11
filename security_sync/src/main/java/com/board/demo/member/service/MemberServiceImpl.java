package com.board.demo.member.service;

import com.board.demo.member.repository.UserRepository;
import com.board.demo.security.Member;
import com.board.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public int addMemberInfo(Member member){
        member.setPw(passwordEncoder.encode(member.getPw()));
        // 회원 저장
        int memberCnt = userRepository.saveMember(member);
        int roleCnt = 0;
        // 기본 권한 저장
        if(memberCnt > 0){
            Role defaultRole = new Role();
            defaultRole.setId(member.getId());
            defaultRole.setRole("USER");
            roleCnt = addMemberRole(defaultRole);
        }

        return roleCnt;
    }

    public int addMemberRole(Role role){
        return userRepository.saveRole(role);
    }

    public Member findMemberById(String id){
        return userRepository.findById(id);
    }

    // 로그인 실패 횟수 증가
    public boolean incrementFailedLoginAttempts(String id){
        int failedLoginCnt = retrieveFailedLoginAttempts(id);
        // 로그인 실패 횟수 증가
        failedLoginCnt++;
        if(failedLoginCnt < 5){
            // 로그인 실패 횟수 저장
            int cnt = userRepository.increaseLoginAttempts(id,failedLoginCnt);
            return cnt > 0;
        }else{
            userRepository.increaseLoginAttempts(id,failedLoginCnt);
            // 계정 잠금
            return lockAccount(id);
        }
    }

    // 로그인 실패 횟수 조회
    public int retrieveFailedLoginAttempts(String id){
        return userRepository.getLoginAttempts(id);
    }

    // 계정 잠금
    public boolean lockAccount(String id){
        return userRepository.setAccountLocked(id) > 0;
    }

    // 계정 잠금 여부 조회
    public boolean checkIfAccountIsLocked(String id){
        return userRepository.isAccountLocked(id);
    }

    // 계정 잠금 해제 및 로그인 실패 횟수 초기화
    public boolean resetFailedLoginAttemptsAndUnlockAccount(String id){
        return userRepository.resetLoginAndUnlockAccount(id) > 0;
    }
}

