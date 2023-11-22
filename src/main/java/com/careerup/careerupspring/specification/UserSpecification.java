package com.careerup.careerupspring.specification;


import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> findByCompany(String company){
        return new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("company"), company);
            }
        };
    }
    public static Specification<UserSkillEntity> findBySkill(String skill){
        return new Specification<UserSkillEntity>() {
            @Override
            public Predicate toPredicate(Root<UserSkillEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("skill"),skill);
            }
        };
    }
    public static Specification<UserFieldEntity> findByField(String field){
        return new Specification<UserFieldEntity>() {
            @Override
            public Predicate toPredicate(Root<UserFieldEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("field"),field);
            }
        };
    }
}
