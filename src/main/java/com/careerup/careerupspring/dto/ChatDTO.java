package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
    private int id;
    private String date;
    private String time;
    private String sessionId;
    private String consult;
    private ChatEntity.status status;

    public ChatEntity toEntity(){
        ChatEntity build = ChatEntity.builder()
                .id(id).date(date).time(time).sessionId(sessionId).consult(consult).status(status).build();
        return build;
    }
}
