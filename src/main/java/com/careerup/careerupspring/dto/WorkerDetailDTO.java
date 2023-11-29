package com.careerup.careerupspring.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDetailDTO {
    private String nickname;
    private String company;
    private List<String> skills;
    private List<String> fields;
    private String profile;
    private String contents;
}
