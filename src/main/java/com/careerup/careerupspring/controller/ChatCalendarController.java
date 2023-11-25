package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
import com.careerup.careerupspring.service.ChatCalendarService;
import com.careerup.careerupspring.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatCalendarController {
    @Autowired
    ChatCalendarService chatCalendarService;

    @GetMapping("/reservation")
    @ResponseBody
    public List<ChatCalendarDTO> getChatCalender(@RequestParam(value = "nickname") String workerNickname) {
        return chatCalendarService.getChatCalender(workerNickname);
    }

//    public void getChatList(@RequestHeader("authorization") String seekerToken, @RequestParam(value = "nickname") String workerNickname){
//        System.out.println("authorazation: " + seekerToken);
//        System.out.println("nickname: "+workerNickname);
//    }


}
