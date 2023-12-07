package com.careerup.careerupspring.entity;

import com.careerup.careerupspring.dto.ChatDTO;
import com.careerup.careerupspring.dto.ChatListDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicInsert
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

    @ColumnDefault("'WAITING'")
    @Enumerated(EnumType.STRING)
    private status status;

    public enum status {
        WAITING, APPROVED, REJECTED, FINISHED
    }

    @OneToMany(mappedBy = "chat")
    private List<ChatUserEntity> chatUsers = new ArrayList<>();

    public ChatListDTO toDTO(){
        ChatListDTO chatListDTO = ChatListDTO.builder()
                .id(id)
                .date(date)
                .time(time)
                .consult(consult)
                .status(status)
                .sessionId(sessionId)
                .build();
        return chatListDTO;
    }
}
