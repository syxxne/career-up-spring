package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.custom.UserRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(){
        super(UserEntity.class);
    }

    @Override
    public List<UserEntity> searchWorkers(String company, List<String> skills, List<String> fields) {
        QUserEntity u = QUserEntity.userEntity;
        QUserSkillEntity s = QUserSkillEntity.userSkillEntity;
        QUserFieldEntity f = QUserFieldEntity.userFieldEntity;

        BooleanExpression companyCondition = eqCompany(company);
        BooleanExpression skillCondition = eqSkill(skills);
        BooleanExpression fieldCondition = eqField(fields);

        List<UserEntity> list = queryFactory.selectFrom(u)
                .leftJoin(u.skills, s).leftJoin(u.fields, f)
                .where(
                        companyCondition != null ? companyCondition : Expressions.TRUE,
                        skillCondition != null ? skillCondition : Expressions.TRUE,
                        fieldCondition != null ? fieldCondition : Expressions.TRUE
                )
                .fetch();

        return list;
    }
    private BooleanExpression eqCompany(String company) {
        if (company == null || company.isEmpty()) return null;
        return QUserEntity.userEntity.company.eq(company);
    }

    private BooleanExpression eqSkill (List<String> skills) {
        if (skills == null || skills.isEmpty()) return null;
        BooleanExpression[] conditions = skills.stream()
                .map(skill -> QUserSkillEntity.userSkillEntity.skill.eq(skill))
                .toArray(BooleanExpression[]::new);
        return Expressions.allOf(conditions);
    }
    private BooleanExpression eqField (List<String> fields) {
        if (fields == null || fields.isEmpty()) return null;
        BooleanExpression[] conditions = fields.stream()
                .map(field -> QUserFieldEntity.userFieldEntity.field.eq(field))
                .toArray(BooleanExpression[]::new);
        return Expressions.allOf(conditions);
    }


}
