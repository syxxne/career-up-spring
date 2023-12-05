package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
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
        UserEntity user = userRepository.findByNickname(nickname).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));

        UUID workerId = user.getId();

        // worker와 연관된 chatId 가져오기
        List<ChatUserEntity> chatUsers = chatUserRepository.findByUserId(workerId);

        List<ChatCalendarDTO> chatCalendarList = new ArrayList<>();

        if (chatUsers.size() != 0) {
            // worker의 채팅 목록 가져오기
            List<ChatEntity> chats = new ArrayList<>();

            for(ChatUserEntity chatUser : chatUsers) {
                ChatEntity chat = chatUser.getChat();
                chats.add(chat);
            }

            List<String> dates = new ArrayList<>();

            // 날짜 & 해당 날짜의 시간 배열로 chatCalendar 값 보내기
            for(int i=0; i < chats.size(); i++) {
                String date = chats.get(i).getDate();

                if(!dates.contains(date)) {
                    dates.add(date);

                    ChatCalendarDTO chatElem = new ChatCalendarDTO();
                    List<String> times = new ArrayList<>();

                    for (ChatEntity chat : chats) {
                        if (chat.getDate().equals(date)) {
                            times.add(chat.getTime());
                        }
                    }

                    chatElem.setDate(date);
                    chatElem.setTime(times);

                    chatCalendarList.add(chatElem);
                }
            }
        }

        return chatCalendarList;
    }
}
