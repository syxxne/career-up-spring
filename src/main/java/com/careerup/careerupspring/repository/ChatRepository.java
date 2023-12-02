package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Integer>{
    public boolean existsBySessionId(String sessionId);
    public Optional<ChatEntity> findBySessionId(String sessionId);
}