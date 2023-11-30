package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatDTO;
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

@Service
public class ChatListService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatUserRepository chatUserRepository;
    @Autowired
    UserRepository userRepository;

    public List<ChatDTO> getChatList(String token){
//        토큰에 저장된 email로 사용자 정보 가져오기
        token = token.substring(7);
        String email = JwtTokenUtil.getUserEmail(token);

        return chatUserRepository.getChatList(email);
    }

}
