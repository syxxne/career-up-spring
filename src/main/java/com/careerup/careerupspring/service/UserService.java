package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public void addUser(UserEntity userEntity){

        userRepository.save(userEntity);
    }

    public List<UserEntity> showAll(){
        return userRepository.findAll();
    }

}
