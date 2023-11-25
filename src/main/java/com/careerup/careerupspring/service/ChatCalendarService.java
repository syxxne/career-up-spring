package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.ChatCalendarDTO;
import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.ChatInfoEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.ChatInfoRepository;
import com.careerup.careerupspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatCalendarService {
    @Autowired
    ChatInfoRepository chatInfoRepository;

    @Autowired
    UserRepository userRepository;

    public List<ChatCalendarDTO> getChatCalender(String workerNickname) {
        Optional<UserEntity> user = userRepository.findByNickname(workerNickname);

        UUID workerId = user.get().getId();

        List<ChatInfoEntity> chats = chatInfoRepository.findByWorkerId(workerId);
        List<ChatCalendarDTO> chatCalendar = new ArrayList<>();

        for (ChatInfoEntity chat : chats) {
            ChatCalendarDTO chatElem = new ChatCalendarDTO();

            chatElem.setId(chat.getId());
            chatElem.setDate(chat.getDate());
            chatElem.setTime(chat.getTime());

            chatCalendar.add(chatElem);
        }
        return chatCalendar;
    }
}
