package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="userSkill")
public class UserSkillEntity {
    @Id
    private UUID id;
    @Column (nullable = false)
    private String skill;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
}
