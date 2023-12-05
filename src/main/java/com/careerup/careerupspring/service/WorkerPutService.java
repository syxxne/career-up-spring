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

import java.io.IOException;
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

        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));
        UserDTO updatedUserDTO = user.toDTO();

        // 파일 전송되었다면 url 업데이트
        try {
            if (file != null && !file.isEmpty()) {
                String imgPath = s3UploadService.upload(file);
                updatedUserDTO.setProfile(imgPath);
            }
        } catch (IOException e){
            throw new IllegalArgumentException("올바르지 않은 파일 형식입니다.");
        }

        // 사용자와 관련된 userField, userSkill 삭제
        userRepository.deleteSkillAndField(userDTO);

        // 사용자의 userField, userSkill 추가
        for (String field : userDTO.getFields()) {
            UserFieldEntity userField = UserFieldEntity.builder()
                    .field(field)
                    .user(user)
                    .build();

            if (!UserFieldEntity.class.isInstance(userFieldRepository.save(userField))){
                throw new RuntimeException("직무 변경에 실패하였습니다.");
            }
        }

        for (String skill : userDTO.getSkills()) {
            UserSkillEntity userSkill = UserSkillEntity.builder()
                    .skill(skill)
                    .user(user)
                    .build();

            if (!UserSkillEntity.class.isInstance(userSkillRepository.save(userSkill))){
                throw new RuntimeException("기술 스택 변경에 실패하였습니다.");
            }
        }

        // 비밀번호 암호화
        if (!userDTO.getPassword().equals("")){
            updatedUserDTO.setPassword(encoder.encode(userDTO.getPassword()));
        }

        // 사용자 프로필 업데이트
        updatedUserDTO.setCompany(userDTO.getCompany());
        updatedUserDTO.setContents(userDTO.getContents());

        if (UserEntity.class.isInstance(userRepository.save(updatedUserDTO.toEntity()))){
            return true;
        } else throw new RuntimeException("정보 수정에 실패하였습니다.");
    }
}
