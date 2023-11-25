package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.NicknameSecondEntity;
import com.careerup.careerupspring.repository.custom.NicknameRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameSecondRepository extends JpaRepository<NicknameSecondEntity, Integer>, NicknameRepositoryCustom {
}
