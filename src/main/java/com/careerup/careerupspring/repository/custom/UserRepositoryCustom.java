package com.careerup.careerupspring.repository.custom;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> searchWorkers(String company, List<String> skills, List<String> fields);
    void putWorker(UserDTO userDTO);
}
