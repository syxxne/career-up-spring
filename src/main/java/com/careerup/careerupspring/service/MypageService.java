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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MypageService {
    private UserRepository userRepository;
    private UserFieldRepository userFieldRepository;
    private final BCryptPasswordEncoder encoder;

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

            // fields
//            userEntity.getFields().clear();
//            //Optional<UserFieldEntity> userFieldEntity = userFieldRepository.findById(userEntity.getId());
//            for (String field : userDTO.getFields()) {
//                UserFieldEntity userField = userEntity.getFields().stream()
//                                .filter( f -> f.getField().equals((field)))
//                                        .findFirst()
//                                                .orElse(new UserFieldEntity());
//                userField.setField(field);
//                userField.setUser(userEntity);
//                userEntity.getFields().add(userField);
//            }
            // 기존 필드 엔티티 목록을 맵으로 변환 (Key: 필드 이름, Value: UserFieldEntity)
            Map<String, UserFieldEntity> existingFields = userEntity.getFields().stream()
                    .collect(Collectors.toMap(UserFieldEntity::getField, Function.identity()));

            // 기존 필드 목록에서 더 이상 필요하지 않은 필드 제거
            userEntity.getFields().removeIf(field -> !userDTO.getFields().contains(field.getField()));

            // DTO에서 제공된 필드 목록을 순회
            for (String field : userDTO.getFields()) {
                UserFieldEntity userField = existingFields.get(field);
                if (userField == null) {
                    // 새 필드 추가
                    userField = new UserFieldEntity();
                    userField.setField(field);
                    userField.setUser(userEntity);
                    userEntity.getFields().add(userField);
                }
            }
            // UserEntity의 필드 목록에서 더 이상 필요하지 않은 필드 제거
            userEntity.getFields().removeIf(field -> !userDTO.getFields().contains(field.getField()));

            // skills
            userEntity.getSkills().clear();
            for (String skill : userDTO.getSkills()) {
                UserSkillEntity userSkill = new UserSkillEntity();
                userSkill.setSkill(skill);
                userSkill.setUser(userEntity);
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