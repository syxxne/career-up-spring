package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.custom.UserRepositoryCustom;
import jakarta.persistence.EnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, UserRepositoryCustom {
    public List<UserEntity> findAllByRoleType(UserEntity.roleType roleType);
}
