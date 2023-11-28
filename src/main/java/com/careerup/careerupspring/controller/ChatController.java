package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
import com.careerup.careerupspring.service.ChatCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    ChatCalendarService chatCalendarService;

    @GetMapping("/reservation/{nickname}")
    @ResponseBody
    public List<ChatCalendarDTO> getChatCalender(@PathVariable String nickname) {
        return chatCalendarService.getChatCalender(nickname);
    }



    @GetMapping("/test")
    @ResponseBody
    public void tokenTest(@RequestHeader("authorization") String seekerToken){
        chatCalendarService.getUserEmail(seekerToken);
        chatCalendarService.getUserRoleType(seekerToken);
    }

}
