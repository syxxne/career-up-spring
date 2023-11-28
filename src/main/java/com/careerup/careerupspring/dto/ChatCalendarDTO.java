package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatCalendarDTO {
    private String date;
    private String time;
}
