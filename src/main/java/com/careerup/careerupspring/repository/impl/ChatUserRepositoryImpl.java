package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.dto.ChatListDTO;
import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.custom.ChatUserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

public class ChatUserRepositoryImpl extends QuerydslRepositorySupport implements ChatUserRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public ChatUserRepositoryImpl(){ super(ChatUserEntity.class);}

    @Override
    public List<ChatListDTO> getChatList(String email){
        try {
            QChatEntity c = QChatEntity.chatEntity;
            QChatUserEntity cu = QChatUserEntity.chatUserEntity;
            QUserEntity u = QUserEntity.userEntity;

            List<ChatListDTO> chatList = new ArrayList<>();

            // email로 사용자와 참여하는 화상채팅 리스트 가져오기
            List<ChatEntity> chatEntityList = queryFactory.selectFrom(c)
                    .leftJoin(c.chatUsers, cu)
                    .where(
                        cu.user.email.eq(email)
                    ).fetch();

            // 나의 닉네임 가져오기
            String myNickname = queryFactory.select(u.nickname)
                    .from(u)
                    .where(
                            u.email.eq(email)
                    ).fetchOne();

            // 화상채팅에 참여하는 상대방의 닉네임을 검색
            for (ChatEntity chat: chatEntityList){
                ChatListDTO chatListDTO = chat.toDTO();
                String otherNickname = queryFactory.select(cu.user.nickname)
                        .from(cu)
                        .where(
                                cu.chat.id.eq(chat.getId()),
                                cu.user.email.ne(email)
                        ).fetchOne();

                // 상대방의 닉네임을 chatDTO에 추가
                 chatListDTO.setOtherNickname(otherNickname);
                 chatListDTO.setMyNickname(myNickname);
                 chatList.add(chatListDTO);
            }
            return chatList;
        } catch (Exception e){
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        }
    }
}
