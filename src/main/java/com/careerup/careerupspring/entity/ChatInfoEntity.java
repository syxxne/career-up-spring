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

    @ManyToOne
    @JoinColumn(name="seeker_id", referencedColumnName = "user_id")
    private UserEntity seeker;

    @ManyToOne
    @JoinColumn(name="worker_id", referencedColumnName = "user_id")
    private UserEntity worker;

    @Column (nullable = false)
    private String date;
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
