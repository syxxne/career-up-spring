package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.UserSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkillEntity, UUID> {
    public List<UserSkillEntity> findAllByUserId(UUID user_id);
}
