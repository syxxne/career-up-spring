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

        try {
            Optional<UserEntity> user = userRepository.findByEmail(userEmail);
            UserDTO updatedUserDTO = user.get().toDTO();
            // 파일 전송되었다면 url 업데이트
            if (file != null && !file.isEmpty()){
                String imgPath = s3UploadService.upload(file);
                updatedUserDTO.setProfile(imgPath);
            }

            // 비밀번호 전송되었다면 암호화
            if (userDTO.getPassword()!=null){
                updatedUserDTO.setPassword(encoder.encode(userDTO.getPassword()));
            }
            userRepository.save(updatedUserDTO.toEntity());
            return true;
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFound " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
