package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.ChatUserEntity;
import com.careerup.careerupspring.entity.UserEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUserDTO {
    private UUID id;
    private UserEntity user;
    private ChatEntity chat;

    public ChatUserEntity toEntity() {
        ChatUserEntity build = ChatUserEntity.builder().id(id).user(user).chat(chat).build();

        return build;
    }
}
