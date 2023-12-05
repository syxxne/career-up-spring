package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatDTO;
import com.careerup.careerupspring.dto.ChatUserDTO;
import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.ChatUserEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.ChatRepository;
import com.careerup.careerupspring.repository.ChatUserRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
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
        if (!ChatEntity.class.isInstance(chatRepository.save(chatDTO.toEntity()))){
            throw new RuntimeException("화상채팅 생성에 실패하였습니다.");
        }

        ChatEntity chat = chatRepository.findBySessionId(sessionId).orElseThrow(()->new EntityNotFoundException("존재하지 않는 화상채팅입니다."));

        // chatUser 테이블에 seeker 정보 저장
        ChatUserDTO chatSeeker = new ChatUserDTO();

        token = token.substring(7);
        String email = JwtTokenUtil.getUserEmail(token);
        UserEntity seeker = userRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 회원입니다."));

        chatSeeker.setUser(seeker);
        chatSeeker.setChat(chat);

        if (!ChatUserEntity.class.isInstance(chatUserRepository.save(chatSeeker.toEntity()))){
            throw new RuntimeException("화상채팅 생성에 실패하였습니다.");
        }

        // chatUser 테이블에 worker 정보 저장
        ChatUserDTO chatWorker = new ChatUserDTO();

        UserEntity worker = userRepository.findByNickname(nickname).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 회원입니다."));

        chatWorker.setUser(worker);
        chatWorker.setChat(chat);

        if (ChatUserEntity.class.isInstance(chatUserRepository.save(chatWorker.toEntity()))){
            return true;
        } else {
            throw new RuntimeException("화상채팅 생성에 실패하였습니다.");
        }
    }

    public int createSessionId() {
        Random random = new Random();

        return random.nextInt(100);
    }
}
