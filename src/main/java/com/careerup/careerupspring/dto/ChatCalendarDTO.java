package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.ChatEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatCalendarDTO {
    private String date;
    private List<String> time;
}
