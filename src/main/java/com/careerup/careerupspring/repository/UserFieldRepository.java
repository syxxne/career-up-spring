package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserFieldRepository extends JpaRepository<UserFieldEntity, UUID> {
    List<UserFieldEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
