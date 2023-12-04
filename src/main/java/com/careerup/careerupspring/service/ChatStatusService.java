package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatStatusDTO;
import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.repository.ChatRepository;
import com.careerup.careerupspring.repository.ChatUserRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatStatusService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatUserRepository chatUserRepository;

    public boolean changeStatus(String token, ChatStatusDTO chatStatusDTO){
        // token 검증
        token = token.substring(7);
        String roleType = JwtTokenUtil.getUserRoleType(token);

        if (roleType.equals("WORKER")) {
            Optional<ChatEntity> chatEntity = chatRepository.findById(chatStatusDTO.getId());
                chatEntity.get().setStatus(chatStatusDTO.getStatus());
                chatRepository.save(chatEntity.get());
                return true;
        } else return false;
//        아닌 경우, 500 internal server error

    }
}
