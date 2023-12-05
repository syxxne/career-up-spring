package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatStatusDTO;
import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.repository.ChatRepository;
import com.careerup.careerupspring.repository.ChatUserRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
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
            ChatEntity chatEntity = chatRepository.findById(chatStatusDTO.getId()).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 화상채팅입니다."));
            chatEntity.setStatus(chatStatusDTO.getStatus());

            if(ChatEntity.class.isInstance(chatRepository.save(chatEntity))) {
                return true;
            } else throw new RuntimeException("상태 변경에 실패하였습니다.");
        } else throw new IllegalStateException("권한이 없습니다.");

    }
}
