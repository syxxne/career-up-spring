package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignInService {
    @Autowired
    UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private long expireTimeMs = 1000*60*60*24;

    //    로그인
    public String signin(String email, String password) {
         UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if (encoder.matches(password, user.getPassword())){
            return JwtTokenUtil.createToken(email, user.getRoleType().toString(), expireTimeMs);
        } else throw new IllegalArgumentException("아이디와 비밀번호가 일치하지 않습니다.");

    }

}
