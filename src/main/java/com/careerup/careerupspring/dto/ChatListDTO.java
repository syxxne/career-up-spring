package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatListDTO {
    private int id;
    private String date;
    private String time;
    private String sessionId;
    private String consult;
    private ChatEntity.status status;
    private String otherNickname;
    private String myNickname;
}
