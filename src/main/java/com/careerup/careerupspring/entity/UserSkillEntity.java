package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="userSkill")
public class UserSkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (nullable = false)
    private String skill;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private UserEntity user;
}
