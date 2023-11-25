package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.ChatInfoEntity;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatInfoRepository extends JpaRepository<ChatInfoEntity, Integer> {
    public List<ChatInfoEntity> findByWorkerId(UUID worker_id);
}
