package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SignUpService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NicknameFirstRepository nicknameFirstRepository;
    @Autowired
    NicknameSecondRepository nicknameSecondRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean signup(UserEntity userEntity) {
        // 이메일 중복 여부 확인
        if (this.isEmailExist(userEntity.getEmail())){
            return false;
        }
        // 비밀번호 암호화
        String pw = encoder.encode(userEntity.getPassword());
        userEntity.setPassword(pw);
        // 닉네임 생성
        String nickname = createNickname();
        while (userRepository.existsByNickname(nickname)) {
            nickname = createNickname();
        }
        userEntity.setNickname(nickname);
        userRepository.save(userEntity);
        return true;
    }

    private String createNickname(){
        String first = nicknameFirstRepository.createNickname();
        String second = nicknameSecondRepository.createNickname();
        String nickname = first + " " + second;
        return nickname;
    }

    private boolean isEmailExist(String email){
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }





}
