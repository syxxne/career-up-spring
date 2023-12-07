package com.careerup.careerupspring.controller;

import com.careerup.careerupspring.dto.*;
import com.careerup.careerupspring.service.ChatCalendarService;
import com.careerup.careerupspring.service.ChatListService;
import com.careerup.careerupspring.service.ChatStatusService;
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
    @Autowired
    ChatStatusService chatStatusService;

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
    public List<ChatListDTO> getChatList(@RequestHeader("authorization") String token){
        return chatListService.getChatList(token);
    }

    @PatchMapping("/chat-status")
    @ResponseBody
    public boolean changeChatStatus(@RequestBody ChatStatusDTO chatStatusDTO, @RequestHeader("authorization") String token){
        return chatStatusService.changeStatus(token, chatStatusDTO);
    }


    @PatchMapping("/chat-finished")
    @ResponseBody
    public boolean finishedChat(@RequestBody ChatFinishedDTO chatFinishedDTO){
        return chatStatusService.finishedChat(chatFinishedDTO);
    }
}
