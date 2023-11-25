package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.dto.UserSigninDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.service.CrawlingService;
import com.careerup.careerupspring.service.SignInService;
import com.careerup.careerupspring.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    SignUpService signUpService;
    @Autowired
    SignInService signInService;
    @Autowired
    CrawlingService crawlingService;
//    (임시) - 닉네임용 db 값 저장 (nickanameFirst, nicknameSecond)
    @GetMapping("/crawling")
    @ResponseBody
    public void crawling() throws IOException {
        crawlingService.firstNickname();
        crawlingService.secondNickname();
    }

//    회원가입
    @PostMapping("/signup")
    @ResponseBody
    public boolean signup(@RequestBody UserDTO userDTO) {
        return signUpService.signup(userDTO.toEntity());
    }

//    로그인
    @PostMapping("/signin")
    @ResponseBody
    public String signin(@RequestBody UserSigninDTO user){
        return signInService.signin(user.getEmail(), user.getPassword());
    }


}
