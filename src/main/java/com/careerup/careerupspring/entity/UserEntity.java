package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Column (nullable = false, length = 31)
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
    public UUID getId() {
        return id;
    }

//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getProfile() {
//        return profile;
//    }
//
//    public void setProfile(String profile) {
//        this.profile = profile;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }



}
