package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GalleryService {
    private UserRepository userRepository;
    
    public void savePost(UserDTO userDTO){
        userRepository.save(userDTO.toEntity());
    }

    public void updateMypage(UserDTO userDTO){
        UserEntity userEntity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userDTO.getEmail()));
System.out.println(userEntity);
        // 사용자 프로필 사진 업데이트
        userEntity.setProfile(userDTO.getProfile());
        userEntity.setEmail(userDTO.getEmail());
        userRepository.save(userEntity);
    }
}
