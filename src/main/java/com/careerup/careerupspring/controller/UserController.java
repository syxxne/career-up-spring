package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.ChatInfoEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @GetMapping("/workers")
    @ResponseBody
    public List<UserDTO> searchWorkers(@RequestParam(value = "company", required = false) String company,
                                       @RequestParam(value="field", required = false) List<String> fields,
                                       @RequestParam(value="skill", required = false) List<String> skills){
        // 전송할 정보: nickname, company, field, skill
            List<UserEntity> workers = userService.searchByOption(company, skills, fields);
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
    }

    // 재직자 상세페이지 ("/workers/{nickname}") - nickname, company, field, skill, contents, profile

    // 화상채팅 신청기능

    // 확인용 주석 임시 추가

}
