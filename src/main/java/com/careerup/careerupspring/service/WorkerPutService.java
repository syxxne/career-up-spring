package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserFieldRepository;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.repository.UserSkillRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkerPutService {
    private final BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @Autowired
    UserFieldRepository userFieldRepository;
    @Autowired
    S3UploadService s3UploadService;
    @Transactional
    // 재직자 회원 정보 (PUT 방식)
    public boolean updatePutMypage(String token, MultipartFile file, UserDTO userDTO) {
        token = token.substring(7);
        String userEmail = JwtTokenUtil.getUserEmail(token);
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));

            // 파일 전송되었다면 url 업데이트
            if (file != null && !file.isEmpty()) {
                String imgPath = s3UploadService.upload(file);
                userDTO.setProfile(imgPath);
            }

            // 사용자와 관련된 userField, userSkill 삭제
            userRepository.deleteSkillAndField(userDTO);

            // 사용자의 userField, userSkill 추가
            for (String field : userDTO.getFields()) {
                UserFieldEntity userField = UserFieldEntity.builder()
                        .field(field)
                        .user(userEntity)
                        .build();
                userEntity.getFields().add(userField);
            }
            for (String skill : userDTO.getSkills()) {
                UserSkillEntity userSkill = UserSkillEntity.builder()
                        .skill(skill)
                        .user(userEntity)
                        .build();
                userEntity.getSkills().add(userSkill);
            }

            // 비밀번호 암호화
            String pw = encoder.encode(userDTO.getPassword());
            userDTO.setPassword(pw);

            // 사용자 프로필 업데이트
            userEntity.setProfile(userDTO.getProfile());
            userEntity.setCompany(userDTO.getCompany());
            userEntity.setContents(userDTO.getContents());
            userEntity.setPassword(pw);

            userRepository.save(userEntity);
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
