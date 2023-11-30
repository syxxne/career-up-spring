package com.careerup.careerupspring.repository.custom;

import com.careerup.careerupspring.dto.ChatDTO;

import java.util.List;

public interface ChatUserRepositoryCustom {
    List<ChatDTO> getChatList(String email);
}
