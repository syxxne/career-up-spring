package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.custom.UserRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(){
        super(UserEntity.class);
    }

    QUserEntity u = QUserEntity.userEntity;
    QUserSkillEntity s = QUserSkillEntity.userSkillEntity;
    QUserFieldEntity f = QUserFieldEntity.userFieldEntity;

    // 재직자 검색
    @Override
    public List<UserEntity> searchWorkers(String company, List<String> skills, List<String> fields) {
        BooleanExpression companyCondition = eqCompany(company);
        BooleanExpression skillCondition = eqSkill(skills);
        BooleanExpression fieldCondition = eqField(fields);

        // company, skill, field 검색어 조건에 적합한 WORKER 찾기
        List<UserEntity> list;
        list = queryFactory.selectFrom(u)
                .leftJoin(u.skills, s).leftJoin(u.fields, f)
                .where(
                        u.roleType.eq(UserEntity.roleType.WORKER),
                        companyCondition != null ? companyCondition : Expressions.TRUE,
                        skillCondition != null ? skillCondition : Expressions.TRUE,
                        fieldCondition != null ? fieldCondition : Expressions.TRUE
                )
                .fetch();

        return list;
    }

    // company 검색어와 일치하는 userEntity 검색
    private BooleanExpression eqCompany(String company) {
        if (company == null || company.isEmpty()) return null;
        return QUserEntity.userEntity.company.eq(company);
    }

    // skills 검색어와 일치하는 userSkillEntity 검색
    private BooleanExpression eqSkill (List<String> skills) {
        if (skills == null || skills.isEmpty()) return null;

        BooleanExpression[] conditions = skills.stream()
                .map(QUserSkillEntity.userSkillEntity.skill::eq)
                .toArray(BooleanExpression[]::new);

        return Expressions.anyOf(conditions);
    }

    // fields 검색어와 일치하는 userFieldEntity 검색
    private BooleanExpression eqField (List<String> fields) {
        if (fields == null || fields.isEmpty()) return null;

        BooleanExpression[] conditions = fields.stream()
                .map(QUserFieldEntity.userFieldEntity.field::eq)
                .toArray(BooleanExpression[]::new);

        return Expressions.anyOf(conditions);
    }

    // 재직자 PUT - Skill, Field 삭제
    @Override
    public void deleteSkillAndField(UserDTO userDTO){
        UUID userId = queryFactory.select(u.id).from(u).where(u.email.eq(userDTO.getEmail())).fetchOne();
        queryFactory.delete(s).where(s.user.id.eq(userId)).execute();
        queryFactory.delete(f).where(f.user.id.eq(userId)).execute();
    }
}
