package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MypageService {
    private UserRepository userRepository;

//    public void savePost(UserDTO userDTO){
//        userRepository.save(userDTO.toEntity());
//    }

    public void updateMypage(String userEmail, UserDTO userDTO){
//        UserEntity userEntity = userRepository.findById(userDTO.getId())
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));
        System.out.println("userEntity" + userEntity);
        // 사용자 프로필 사진 업데이트
        userEntity.setProfile(userDTO.getProfile());
        userEntity.setEmail(userDTO.getEmail());
        userRepository.save(userEntity);
    }
}
