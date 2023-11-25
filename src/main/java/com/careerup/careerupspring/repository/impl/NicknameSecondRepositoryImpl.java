package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.entity.NicknameFirstEntity;
import com.careerup.careerupspring.entity.NicknameSecondEntity;
import com.careerup.careerupspring.entity.QNicknameFirstEntity;
import com.careerup.careerupspring.entity.QNicknameSecondEntity;
import com.careerup.careerupspring.repository.custom.NicknameRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class NicknameSecondRepositoryImpl extends QuerydslRepositorySupport implements NicknameRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public NicknameSecondRepositoryImpl() {super(NicknameFirstEntity.class);}

    @Override
    public String createNickname(){
        NicknameSecondEntity second = queryFactory.selectFrom(QNicknameSecondEntity.nicknameSecondEntity)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .fetchFirst();
        return second.getNoun();
    }
}
