package com.careerup.careerupspring.repository.impl;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.*;
import com.careerup.careerupspring.repository.custom.UserRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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

    @Override
    public List<UserEntity> searchWorkers(String company, List<String> skills, List<String> fields) {
        //QUserEntity u = QUserEntity.userEntity;
        //QUserSkillEntity s = QUserSkillEntity.userSkillEntity;
        //QUserFieldEntity f = QUserFieldEntity.userFieldEntity;

        BooleanExpression companyCondition = eqCompany(company);
        BooleanExpression skillCondition = eqSkill(skills);
        BooleanExpression fieldCondition = eqField(fields);

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
    private BooleanExpression eqCompany(String company) {
        if (company == null || company.isEmpty()) return null;
        return QUserEntity.userEntity.company.eq(company);
    }

    private BooleanExpression eqSkill (List<String> skills) {
        if (skills == null || skills.isEmpty()) return null;
        BooleanExpression[] conditions = skills.stream()
                .map(QUserSkillEntity.userSkillEntity.skill::eq)
                .toArray(BooleanExpression[]::new);
        return Expressions.anyOf(conditions);
    }
    private BooleanExpression eqField (List<String> fields) {
        if (fields == null || fields.isEmpty()) return null;
        BooleanExpression[] conditions = fields.stream()
                .map(QUserFieldEntity.userFieldEntity.field::eq)
                .toArray(BooleanExpression[]::new);
        return Expressions.anyOf(conditions);
    }

    @Override
    public void putWorker(UserDTO userDTO){
        UUID userId = queryFactory.select(u.id).from(u).where(u.email.eq(userDTO.getEmail())).fetchOne();
        queryFactory.update(u)
                .set(u.profile, userDTO.getProfile())
                .set(u.company, userDTO.getCompany())
                .set(u.contents, userDTO.getContents())
                .set(u.password, userDTO.getPassword())
                // skill, field도 한번에 될지
                .where(
                        u.id.eq(userId)
                ).execute();
    }
}
