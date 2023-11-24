package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserFieldRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.repository.UserSkillRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private long expireTimeMs = 1000*60*60*24;

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
                return JwtTokenUtil.createToken(email, user.get().getNickname(), user.get().getRoleType().toString(), expireTimeMs, secretkey);
            } else return "no";
        } else return "no";
    }

    private boolean isEmailExist(String email){
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

//    재직자 리스트 (페이지 요청, 검색)
    public List<UserDTO> searchByOption(String company, List<String> skills, List<String> fields){
        List<UserEntity> workers = userRepository.searchWorkers(company, skills, fields);
        List<UserDTO> workerLists = new ArrayList<>();
        for (UserEntity worker : workers) {
            UserDTO workerElem = new UserDTO();
            workerElem.setNickname(worker.getNickname());
            workerElem.setCompany(worker.getCompany());
            List<String> userSkills = new ArrayList<>();
            List<String> userFields = new ArrayList<>();
            for (UserSkillEntity userSkill: worker.getSkills()){
                userSkills.add(userSkill.getSkill());
            }
            for (UserFieldEntity userField: worker.getFields()){
                userFields.add(userField.getField());
            }
            workerElem.setSkills(userSkills);
            workerElem.setFields(userFields);
            workerLists.add(workerElem);
        }
        return workerLists;
//        return userRepository.searchWorkers(company, skills, fields);
    }




}
