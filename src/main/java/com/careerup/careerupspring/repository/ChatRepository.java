package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.ChatEntity;
import com.careerup.careerupspring.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {
    public boolean existsBySessionId(String sessionId);
    public Optional<ChatEntity> findBySessionId(String sessionId);
}