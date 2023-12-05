package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SeekerPatchService {
    private final BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    S3UploadService s3UploadService;

    // 구직자 회원 정보 (PATCH 방식)
    public boolean updatePatchMypage(String token, MultipartFile file, UserDTO userDTO) {
        token = token.substring(7);
        String userEmail = JwtTokenUtil.getUserEmail(token);

        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));
        UserDTO updatedUserDTO = user.toDTO();

        // 파일 전송되었다면 url 업데이트
        try {
            if (file != null && !file.isEmpty()){
                String imgPath = s3UploadService.upload(file);
                updatedUserDTO.setProfile(imgPath);
            }
        } catch (IOException e){
            throw new IllegalArgumentException("올바르지 않은 파일 형식입니다.");
        }

        // 비밀번호 전송되었다면 암호화
        if (!userDTO.getPassword().equals("")){
            updatedUserDTO.setPassword(encoder.encode(userDTO.getPassword()));
        }

        if (UserEntity.class.isInstance(userRepository.save(updatedUserDTO.toEntity()))) {
            return true;
        } else throw new RuntimeException("정보 수정에 실패하였습니다.");
    }
}
