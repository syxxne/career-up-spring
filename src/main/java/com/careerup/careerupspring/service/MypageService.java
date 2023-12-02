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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MypageService {
    private final BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @Autowired
    UserFieldRepository userFieldRepository;

    // 페이지 요청 (GET 방식)
    public UserDTO getMyPage(String token) {
        token = token.substring(7);
        String userEmail = JwtTokenUtil.getUserEmail(token);

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEmail);
        UserEntity userEntity = userEntityOptional.orElseThrow(() -> new EntityNotFoundException("사용자 없음" + userEmail));

        UserDTO userDTO = userEntity.toDTO();

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

}