package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.UserFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFieldRepository extends JpaRepository<UserFieldEntity, UUID> {
}
