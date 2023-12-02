package com.careerup.careerupspring.repository.custom;


import com.careerup.careerupspring.dto.ChatListDTO;

import java.util.List;

public interface ChatUserRepositoryCustom {
    List<ChatListDTO> getChatList(String email);
}
