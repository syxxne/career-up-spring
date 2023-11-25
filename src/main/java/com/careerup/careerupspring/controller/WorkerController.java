package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WorkerController {
    @Autowired
    SearchService searchService;
    //    재직자 리스트 (페이지 요청, 검색)
    @GetMapping("/workers")
    @ResponseBody
    public List<UserDTO> searchWorkers(@RequestParam(value = "company", required = false) String company,
                                       @RequestParam(value="field", required = false) List<String> fields,
                                       @RequestParam(value="skill", required = false) List<String> skills){
        // 전송할 정보: nickname, company, field, skill
        return searchService.searchByOption(company, skills, fields);
    }

    // 재직자 상세페이지 ("/workers/{nickname}") - nickname, company, field, skill, contents, profile


}
