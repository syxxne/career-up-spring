package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDTO {
    private UUID id;
    private String email;
    private String password;
    private String profile;
    private String nickname;
    private String company;
    private List<String> fields;
    private List<String> skills;

    public UserEntity toEntity(){
        UserEntity build = UserEntity.builder()
                .id(id).profile(profile).build();
        return build;
    }

}
