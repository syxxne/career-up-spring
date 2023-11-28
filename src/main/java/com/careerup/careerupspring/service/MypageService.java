package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MypageService {
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

//    public void savePost(UserDTO userDTO){
//        userRepository.save(userDTO.toEntity());
//    }

    public void updatePatchMypage(String userEmail, UserDTO userDTO) {
//        UserEntity userEntity = userRepository.findById(userDTO.getId())
        try {
            System.out.println("Start update");
            UserEntity userEntity = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));
            System.out.println("userEntity" + userEntity);
            // optional로 수정

            // 사용자 프로필 사진 업데이트
            userEntity.setProfile(userDTO.getProfile());
            userEntity.setEmail(userEmail);

            // 비밀번호 암호화
            String pw = encoder.encode(userDTO.getPassword());
            userEntity.setPassword(pw);

            userRepository.save(userEntity);
            System.out.println("Update completed");
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFound " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePutMypage(String userEmail, UserDTO userDTO) {
//      UserEntity userEntity = userRepository.findById(userDTO.getId())
        try {
            System.out.println("Start update");
//          아래는 이메일을 통해서 get함
          UserEntity userEntity = userRepository.findByEmail(userEmail)
                  .orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));
          //optional로 수정

            System.out.println("유저 테이블 : " + userEntity);
            // 사용자 프로필 사진 업데이트
            userEntity.setProfile(userDTO.getProfile());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPassword(userDTO.getPassword());
            userEntity.setCompany(userDTO.getCompany());
            userEntity.setContents(userDTO.getContents());
            List<UserFieldEntity> fields = new ArrayList<>();
            for (String field : userDTO.getFields()) {
                UserFieldEntity userField = UserFieldEntity.builder()
                        .field(field)
                        .user(userEntity)
                        .build();
                fields.add(userField);
            }
            userEntity.setFields(fields);
            List<UserSkillEntity> skills = new ArrayList<>();
            for (String skill : userDTO.getSkills()) {
                UserSkillEntity userSkill = UserSkillEntity.builder()
                        .skill(skill)
                        .user(userEntity)
                        .build();
                skills.add(userSkill);
            }
            userEntity.setSkills(skills);

            // 비밀번호 암호화
            String pw = encoder.encode(userDTO.getPassword());
            userEntity.setPassword(pw);

            userRepository.save(userEntity);
            System.out.println("Update completed");
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFound " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}