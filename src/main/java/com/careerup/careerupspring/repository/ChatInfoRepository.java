package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.ChatInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatInfoRepository extends JpaRepository<ChatInfoEntity, Integer> {
}
