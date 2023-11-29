package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MypageService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    //private final JwtTokenUtil jwtTokenUtil;

    public UserDTO getMyPage(String userEmail) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
        UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setRoleType(userEntity.getRoleType());
        userDTO.setNickname(userEntity.getNickname());
        userDTO.setProfile(userEntity.getProfile());
        userDTO.setCompany(userEntity.getCompany());
        userDTO.setContents(userEntity.getContents());

        List<String> fields = userEntity.getFields().stream()
                .map(UserFieldEntity::getField)
                .collect(Collectors.toList());
        userDTO.setFields(fields);

        List<String> skills = userEntity.getSkills().stream()
                .map(UserSkillEntity::getSkill)
                .collect(Collectors.toList());
        userDTO.setSkills(skills);

        return userDTO;
    }

    public void updatePatchMypage(String userEmail, UserDTO userDTO) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));
            // 사용자 프로필 사진 업데이트
            userEntity.setProfile(userDTO.getProfile());
            // 비밀번호 암호화
            String pw = encoder.encode(userDTO.getPassword());
            userEntity.setPassword(pw);

            userRepository.save(userEntity);
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFound " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePutMypage(String userEmail, UserDTO userDTO) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));

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

        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFound " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}