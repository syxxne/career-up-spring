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
@Table(name="userField")
public class UserFieldEntity {
    @Id
    private UUID id;

    @Column (nullable = false)
    private String field;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
}
