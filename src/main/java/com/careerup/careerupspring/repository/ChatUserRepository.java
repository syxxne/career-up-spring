package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.ChatUserEntity;
import com.careerup.careerupspring.repository.custom.ChatUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUserEntity, UUID>, ChatUserRepositoryCustom {
    public List<ChatUserEntity> findByUserId(UUID userId);
}