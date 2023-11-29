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
        // worker 정보 가져오기
        Optional<UserEntity> user = userRepository.findByNickname(nickname);

        UUID workerId = user.get().getId();

        // worker의 채팅 목록 가져오기
        List<ChatUserEntity> chatUsers = chatUserRepository.findByUserId(workerId);

        List<ChatCalendarDTO> chatCalendarList = new ArrayList<>();

        // 채팅 목록에서 날짜, 시간 정보만 추출하여 calendar 리스트 생성
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
}
