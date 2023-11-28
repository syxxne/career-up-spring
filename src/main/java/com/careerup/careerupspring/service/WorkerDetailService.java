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
        Optional<UserEntity> worker = userRepository.findByNickname(nickname);
        List<String> userSkills = new ArrayList<>();
        List<String> userFields = new ArrayList<>();
        for (UserSkillEntity userSkill: worker.get().getSkills()){
            userSkills.add(userSkill.getSkill());
        }
        for (UserFieldEntity userField: worker.get().getFields()){
            userFields.add(userField.getField());
        }

        UserDTO workerDetail = UserDTO.builder()
                .email(worker.get().getEmail())
                .nickname(worker.get().getNickname())
                .profile(worker.get().getProfile())
                .company(worker.get().getCompany())
                .contents(worker.get().getContents())
                .skills(userSkills)
                .fields(userFields).build();
        return workerDetail;
    }
}
