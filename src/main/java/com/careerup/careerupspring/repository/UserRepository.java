package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, UserRepositoryCustom {
    public boolean existsByNickname(String nickname);
    public Optional<UserEntity> findByEmail(String email);
    public UserEntity findByNickname(String nickname);
}
