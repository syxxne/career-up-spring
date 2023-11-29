package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatDTO;
import com.careerup.careerupspring.dto.ChatUserDTO;
import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.ChatRepository;
import com.careerup.careerupspring.repository.ChatUserRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ReserveChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatUserRepository chatUserRepository;

    @Autowired
    UserRepository userRepository;

    public boolean reserveChat(String nickname, String token, ChatDTO chatDTO) {
        // sessionId 생성
        String sessionId = "session";

        do {
            sessionId += createSessionId();
        } while (chatRepository.existsBySessionId(sessionId));

        chatDTO.setSessionId(sessionId);

        // chat 테이블에 저장
        chatRepository.save(chatDTO.toEntity());

        Optional<ChatEntity> chat = chatRepository.findBySessionId(sessionId);

        // chatUser 테이블에 seeker 정보 저장
        ChatUserDTO chatSeeker = new ChatUserDTO();

        token = token.substring(7);
        String email = JwtTokenUtil.getUserEmail(token);
        Optional<UserEntity> seeker = userRepository.findByEmail(email);

        chatSeeker.setUser(seeker.get());
        chatSeeker.setChat(chat.get());

        chatUserRepository.save(chatSeeker.toEntity());

        // chatUser 테이블에 worker 정보 저장
        ChatUserDTO chatWorker = new ChatUserDTO();

        Optional<UserEntity> worker = userRepository.findByNickname(nickname);

        chatWorker.setUser(worker.get());
        chatWorker.setChat(chat.get());

        chatUserRepository.save(chatWorker.toEntity());

        return true;
    }

    public int createSessionId() {
        Random random = new Random();

        return random.nextInt(100);
    }
}
