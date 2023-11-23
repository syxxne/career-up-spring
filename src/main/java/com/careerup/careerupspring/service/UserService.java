package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserFieldRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.repository.UserSkillRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @Autowired
    UserFieldRepository userFieldRepository;

    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.key}")
    private String secretkey;

//    회원가입
    public boolean signIn(UserEntity userEntity) {
        // 이메일 중복 여부 확인
        if (this.isEmailExist(userEntity.getEmail())){
            return false;
        }
        // 비밀번호 암호화
        String pw = encoder.encode(userEntity.getPassword());
        userEntity.setPassword(pw);
        // 닉네임 랜덤 생성
        userRepository.save(userEntity);
        return true;
    }

//    로그인
    public String signup(String email, String password){
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            if (encoder.matches(password, user.get().getPassword())){
                return JwtTokenUtil.createToken(email, user.get().getNickname(), secretkey);
            } else return "no";
        } else return "no";
    }

    private boolean isEmailExist(String email){
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

//    재직자 리스트 (페이지 요청, 검색)
    public List<UserEntity> searchByOption(String company, List<String> skills, List<String> fields){
        return userRepository.searchWorkers(company, skills, fields);
    }




}
