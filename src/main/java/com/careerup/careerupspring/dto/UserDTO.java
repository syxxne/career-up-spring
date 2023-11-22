package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String nickname;
    private String company;
    private List<String> fields;
    private List<String> skills;
}
