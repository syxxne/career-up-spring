package com.careerup.careerupspring.service;

import com.careerup.careerupspring.dto.UserDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerDetailService {
    @Autowired
    UserRepository userRepository;

    public UserDTO workerDetail(String nickname){
        UserEntity worker = userRepository.findByNickname(nickname);
        List<String> userSkills = new ArrayList<>();
        List<String> userFields = new ArrayList<>();
        for (UserSkillEntity userSkill: worker.getSkills()){
            userSkills.add(userSkill.getSkill());
        }
        for (UserFieldEntity userField: worker.getFields()){
            userFields.add(userField.getField());
        }
        UserDTO workerDetail = UserDTO.builder()
                .email(worker.getEmail())
                .nickname(worker.getNickname())
                .profile(worker.getProfile())
                .company(worker.getCompany())
                .contents(worker.getContents())
                .skills(userSkills)
                .fields(userFields).build();
        return workerDetail;
    }
}
