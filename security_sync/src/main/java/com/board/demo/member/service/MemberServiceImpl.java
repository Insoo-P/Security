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

//    @Autowired
//    private PasswordEncoderConfig passwordEncoder;

    // 유저가 존재하는지 여부를 확인
    @Override
    public boolean checkIfIdExists(String id) {
        return userRepository.existsById(id);
    }

    // Email이 존재하는지 여부를 확인
    @Override
    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // 유저 정보 조회 (MEMBER, ROLES 포함)
    @Override
    public Member findMemberById(String id){
        return userRepository.findById(id);
    }

    // 유저 정보 저장 (MEMBER, ROLES 포함)
    @Transactional
    @Override
    public boolean addMemberInfo(Member member){
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
        return roleCnt > 0;
    }

    // 유저 정보 수정
    @Override
    public boolean modifyMemberInfo(Member member) {
        return userRepository.updateMember(member) > 0;
    }

    // 유저 권한 저장
    @Transactional
    @Override
    public int addMemberRole(Role role){
        return userRepository.saveRole(role);
    }



    // 로그인 실패 횟수 증가
    @Override
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
    @Override
    public int retrieveFailedLoginAttempts(String id){
        return userRepository.getLoginAttempts(id);
    }

    // 계정 잠금
    @Override
    public boolean lockAccount(String id){
        return userRepository.setAccountLocked(id) > 0;
    }

    // 계정 잠금 여부 조회
    @Override
    public boolean checkIfAccountIsLocked(String id){
        return userRepository.isAccountLocked(id);
    }

    // 계정 잠금 해제 및 로그인 실패 횟수 초기화
    @Override
    public boolean resetFailedLoginAttemptsAndUnlockAccount(String id){
        return userRepository.resetLoginAndUnlockAccount(id) > 0;
    }
}

