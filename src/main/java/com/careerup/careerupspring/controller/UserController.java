package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.dto.UserSigninDTO;
import com.careerup.careerupspring.entity.ChatInfoEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.service.UserService;
import org.apache.catalina.valves.CrawlerSessionManagerValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
//    (임시) - 닉네임용 db 값 저장 (nickanameFirst, nicknameSecond)
    @GetMapping("/crawling")
    @ResponseBody
    public void crawling() throws IOException {
        userService.firstNickname();
        userService.secondNickname();
    }

//    회원가입
    @PostMapping("/signup")
    @ResponseBody
    public boolean signup(@RequestBody UserEntity userEntity) {
        return userService.signup(userEntity);
    }

//    로그인
    @PostMapping("/signin")
    @ResponseBody
    public String signin(@RequestBody UserSigninDTO user){
        return userService.signin(user.getEmail(), user.getPassword());
    }

//    재직자 리스트 (페이지 요청, 검색)
    @GetMapping("/workers")
    @ResponseBody
    public List<UserDTO> searchWorkers(@RequestParam(value = "company", required = false) String company,
                                       @RequestParam(value="field", required = false) List<String> fields,
                                       @RequestParam(value="skill", required = false) List<String> skills){
        // 전송할 정보: nickname, company, field, skill
           return userService.searchByOption(company, skills, fields);
    }

    // 재직자 상세페이지 ("/workers/{nickname}") - nickname, company, field, skill, contents, profile



}
