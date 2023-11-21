package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue (generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id")
    private UUID id;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false, length = 8)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private roleType roleType;
    @Column(columnDefinition = "TEXT")
    private String contents;
    private String profile;
    private String company;

    public enum roleType {
        SEEKER, WORKER
    }
    @OneToMany(mappedBy = "user")
    private List<UserFieldEntity> fields = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserSkillEntity>skills = new ArrayList<>();
}
