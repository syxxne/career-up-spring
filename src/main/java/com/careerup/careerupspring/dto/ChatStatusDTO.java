package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import lombok.Getter;

@Getter
public class ChatStatusDTO {
    private int id;
    private ChatEntity.status status;
}
