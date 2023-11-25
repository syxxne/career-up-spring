package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.*;
import com.careerup.careerupspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @Autowired
    UserFieldRepository userFieldRepository;
    @Autowired
    NicknameFirstRepository nicknameFirstRepository;
    @Autowired
    NicknameSecondRepository nicknameSecondRepository;

    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.key}")
    private String secretkey;
    private long expireTimeMs = 1000*60*60*24;

//    회원가입
    public boolean signup(UserEntity userEntity) {
        // 이메일 중복 여부 확인
        if (this.isEmailExist(userEntity.getEmail())){
            return false;
        }

        // 비밀번호 암호화
        String pw = encoder.encode(userEntity.getPassword());
        userEntity.setPassword(pw);
        // 닉네임 랜덤 생성
        userEntity.setNickname(createNickname());
        userRepository.save(userEntity);
        return true;
    }

//    로그인
    public String signin(String email, String password){
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            if (encoder.matches(password, user.get().getPassword())){
                return JwtTokenUtil.createToken(email, user.get().getRoleType().toString(), expireTimeMs, secretkey);
            } else return "no";
        } else return "no";
    }

    // 닉네임 생성
    private String createNickname(){
        String first = nicknameFirstRepository.createNickname();
        String second = nicknameSecondRepository.createNickname();
        String nickname = first + " " + second;
        while (userRepository.findByNickname(nickname).isPresent()){
            createNickname();
        }
        return nickname;
    }
    public void firstNickname() throws IOException {
        Document document = Jsoup.connect("https://ko.wiktionary.org/wiki/%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%EA%B4%80%ED%98%95%EC%82%AC%ED%98%95(%ED%98%95%EC%9A%A9%EC%82%AC)").get();
        Iterator<Element> content = document.select(".mw-category-group li a").iterator();
        while(content.hasNext()){
            String name = content.next().text();
            NicknameFirstEntity nicknameFirst = new NicknameFirstEntity();
            nicknameFirst.setAdj(name);
            nicknameFirstRepository.save(nicknameFirst);
        }
    }

    public void secondNickname() throws IOException{
        Document document = Jsoup.connect("https://ko.wiktionary.org/wiki/%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%ED%8F%AC%EC%9C%A0%EB%A5%98").get();
        Iterator<Element> content = document.select(".mw-category-group li a").iterator();
        while(content.hasNext()){
            String name = content.next().text();
            NicknameSecondEntity nicknameSecond = new NicknameSecondEntity();
            nicknameSecond.setNoun(name);
            nicknameSecondRepository.save(nicknameSecond);
        }
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
