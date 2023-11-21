package com.careerup.careerupspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chatInfo")
public class ChatInfoEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false)
    private UUID seekerId;

    @Column (nullable = false)
    private UUID workerId;

    @Column (nullable = false)
    private String data;
    @Column (nullable = false)
    private String time;
    @Column
    private String consult;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAIT'")
    @Column (nullable = false)
    private status status;

    public enum status {
        WAIT, APPROVED, REJECTED
    }

}
