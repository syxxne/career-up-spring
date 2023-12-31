package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.entity.NicknameFirstEntity;
import com.careerup.careerupspring.entity.QNicknameFirstEntity;
import com.careerup.careerupspring.repository.custom.NicknameRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class NicknameFirstRepositoryImpl extends QuerydslRepositorySupport implements NicknameRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public NicknameFirstRepositoryImpl() {super(NicknameFirstEntity.class);}

    @Override
    public String createNickname(){
        NicknameFirstEntity first = queryFactory.selectFrom(QNicknameFirstEntity.nicknameFirstEntity)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .fetchFirst();
        return first.getAdj();
    }
}
