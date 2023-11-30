package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.dto.ChatDTO;
import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.custom.ChatUserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

public class ChatUserRepositoryImpl extends QuerydslRepositorySupport implements ChatUserRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public ChatUserRepositoryImpl(){ super(ChatUserEntity.class);}

    @Override
    public List<ChatDTO> getChatList(String email){
        QChatEntity c = QChatEntity.chatEntity;
        QChatUserEntity cu = QChatUserEntity.chatUserEntity;

        List<ChatDTO> chatList = new ArrayList<>();

//        email로 사용자와 참여하는 화상채팅 리스트 가져오기
        List<ChatEntity> chatEntityList = queryFactory.selectFrom(c)
                .leftJoin(c.chatUsers, cu)
                .where(
                        cu.user.email.eq(email)
                ).fetch();

//        화상채팅에 참여하는 상대방의 닉네임을 검색
        for (ChatEntity chat: chatEntityList){
            ChatDTO chatDTO = chat.toDTO();
            String nickname = queryFactory.select(cu.user.nickname)
                    .from(cu)
                    .where(
                            cu.chat.id.eq(chat.getId()),
                            cu.user.email.ne(email)
                    ).fetchOne();

//          상대방의 닉네임을 chatDTO에 추가
            chatDTO.setNickname(nickname);
            chatList.add(chatDTO);
        }
        return chatList;
    }

}
