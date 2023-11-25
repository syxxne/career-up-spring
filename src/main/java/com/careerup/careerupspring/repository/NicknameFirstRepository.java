package com.careerup.careerupspring.repository;

import com.careerup.careerupspring.entity.NicknameFirstEntity;
import com.careerup.careerupspring.repository.custom.NicknameRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameFirstRepository extends JpaRepository<NicknameFirstEntity, Integer>, NicknameRepositoryCustom {

}
