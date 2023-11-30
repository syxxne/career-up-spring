package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import com.careerup.careerupspring.repository.UserSkillRepository;
import com.careerup.careerupspring.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    // 페이지 요청 (GET 방식)
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

        // 사용자 fields 정보 추출
        List<String> fields = userEntity.getFields().stream()
                .map(UserFieldEntity::getField)
                .collect(Collectors.toList());
        userDTO.setFields(fields);

        // 사용자 skills 정보 추출
        List<String> skills = userEntity.getSkills().stream()
                .map(UserSkillEntity::getSkill)
                .collect(Collectors.toList());
        userDTO.setSkills(skills);

        return userDTO;
    }

    // 구직자 회원 정보 (PATCH 방식)
    public void updatePatchMypage(String userEmail, UserDTO userDTO) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));
            // 사용자 프로필 업데이트
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

    // 재직자 회원 정보 (PUT 방식)
    @Transactional
    public void updatePutMypage(String userEmail, UserDTO userDTO) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));

            // 사용자 프로필 업데이트
            userEntity.setProfile(userDTO.getProfile());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPassword(userDTO.getPassword());
            userEntity.setCompany(userDTO.getCompany());
            userEntity.setContents(userDTO.getContents());

            // DTO에서 fields 정보 추출 후 userEntity에 연결된 엔티티 리스트를 생성하고 설정
            userEntity.getFields().clear();
            for (String field : userDTO.getFields()) {
                UserFieldEntity userField = UserFieldEntity.builder()
                        .field(field)
                        .user(userEntity)
                        .build();
                userEntity.getFields().add(userField);
            }

            // DTO에서 skills 정보 추출 후 userEntity에 연결된 엔티티 리스트를 생성하고 설정
            userEntity.getSkills().clear();
            for (String skill : userDTO.getSkills()) {
                UserSkillEntity userSkill = UserSkillEntity.builder()
                        .skill(skill)
                        .user(userEntity)
                        .build();
                userEntity.getSkills().add(userSkill);
            }


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