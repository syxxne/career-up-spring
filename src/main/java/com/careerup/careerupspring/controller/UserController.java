package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.entity.ChatInfoEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    // (임시) 사용자 추가 - 테이블 정보 저장 확인용으로 작성한 임시코드입니다. (삭제 예정)
    // 테이블 생성 후 application.properties에서 update로 수정 필요.
    @PostMapping("/user")
    @ResponseBody
    public void addUser(@RequestBody UserEntity userEntity){
        userService.addUser(userEntity);
        System.out.println("저장완료");
        List<UserEntity> lists = userService.showAllWorkers();
        System.out.println("사용자 정보"+lists.get(0).getId());
    }

    // 검색없이 재직자 페이지 요청 시 -> 전체 리스트
    @GetMapping("/workers")
    @ResponseBody
    public void showAllWorkers(){
        userService.showAllWorkers();
    }
    // 재직자 검색 기능
    // 재직자 상세페이지

    // 화상채팅 신청기능

}
