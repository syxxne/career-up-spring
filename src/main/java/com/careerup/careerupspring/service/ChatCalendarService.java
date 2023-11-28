package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.ChatUserEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.ChatRepository;
import com.careerup.careerupspring.repository.ChatUserRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatCalendarService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatUserRepository chatUserRepository;

    @Autowired
    UserRepository userRepository;

    public List<ChatCalendarDTO> getChatCalender(String nickname) {
        Optional<UserEntity> user = userRepository.findByNickname(nickname);

        UUID workerId = user.get().getId();

        List<ChatUserEntity> chatUsers = chatUserRepository.findByUserId(workerId);
        List<ChatCalendarDTO> chatCalendarList = new ArrayList<>();

        if (chatUsers.size() != 0) {
            for(ChatUserEntity chatUser : chatUsers) {
                ChatCalendarDTO chatCalendarElem = new ChatCalendarDTO();

                chatCalendarElem.setTime(chatUser.getChat().getTime());
                chatCalendarElem.setDate(chatUser.getChat().getDate());

                chatCalendarList.add(chatCalendarElem);
            }
        }

        return chatCalendarList;
    }
    public String getUserEmail (String token) {
        token = token.substring(7);
        String email = JwtTokenUtil.getUserEmail(token);
        System.out.println(email);
        return email;
    }

    public String getUserRoleType (String token) {
        token = token.substring(7);
        String roleType = JwtTokenUtil.getUserRoleType(token);
        System.out.println(roleType);
        return roleType;
    }
}
