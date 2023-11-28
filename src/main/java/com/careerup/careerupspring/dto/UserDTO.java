package com.careerup.careerupspring.dto;

import com.careerup.careerupspring.entity.UserEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private UUID id;
    private String email;
    private String password;
    private String profile;
    private String nickname;
    private String company;
    private String contents;
    private UserEntity.roleType roleType;
    private List<String> fields;
    private List<String> skills;

    public UserEntity toEntity(){
        UserEntity build = UserEntity.builder()
                .email(email)
                .roleType(roleType)
                .password(password)
                .company(company)
                .id(id).profile(profile).build();
        return build;
    }

}
