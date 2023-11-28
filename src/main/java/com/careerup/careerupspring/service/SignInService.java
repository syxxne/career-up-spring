package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public String signin(String email, String password){
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            if (encoder.matches(password, user.get().getPassword())){
                return JwtTokenUtil.createToken(email, user.get().getRoleType().toString(), expireTimeMs);
            } else return "no";
        } else return "no";
    }

}
