package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatId")
    private int id;

    @Column(nullable = false)
    private String date;

    @Column (nullable = false)
    private String time;

    @Column (nullable = false)
    private String sessionId;

    @Column
    private String consult;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAITING'")
    @Column (nullable = false)
    private status status;

    public enum status {
        WAITING, APPROVED, REJECTED
    }
}
