package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
import com.careerup.careerupspring.dto.ChatDTO;
import com.careerup.careerupspring.service.ChatCalendarService;
import com.careerup.careerupspring.service.ChatListService;
import com.careerup.careerupspring.service.ReserveChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    ChatCalendarService chatCalendarService;

    @Autowired
    ReserveChatService reserveChatService;

    @Autowired
    ChatListService chatListService;

    @GetMapping("/reservation/{nickname}")
    @ResponseBody
    public List<ChatCalendarDTO> getChatCalender(@PathVariable String nickname) {
        return chatCalendarService.getChatCalender(nickname);
    }

    @PostMapping("/reservation/{nickname}")
    @ResponseBody
    public boolean reserveChat(@PathVariable String nickname, @RequestHeader("authorization") String token, @RequestBody ChatDTO chatDTO) {
        return reserveChatService.reserveChat(nickname, token, chatDTO);
    }

    @GetMapping("/chats")
    @ResponseBody
    public List<ChatDTO> getChatList(@RequestHeader("authorization") String token){
        return chatListService.getChatList(token);
    }
}
