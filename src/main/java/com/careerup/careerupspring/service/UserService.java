package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserFieldRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.repository.UserSkillRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @Autowired
    UserFieldRepository userFieldRepository;
    public void addUser(UserEntity userEntity){

        userRepository.save(userEntity);
    }

    public List<UserEntity> showAllWorkers(){
        return userRepository.findAllByRoleType(UserEntity.roleType.WORKER);
    }

    public List<UserSkillEntity> searchBySkill(UUID id){
        return userSkillRepository.findAllByUserId(id);
    }

}
