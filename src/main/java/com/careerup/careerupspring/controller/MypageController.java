package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class MypageController {

    @Autowired
    MypageService mypageService;

    // 마이페이지 요청
    @GetMapping("/mypage")
    public void getPage(){}

    // 구직자 수정
    @PatchMapping("/mypage")
    public void patchPage(){}

    // 재직자 작성 및 수정
    @PutMapping("/mypage")
    public void putPage(){

    }

}
