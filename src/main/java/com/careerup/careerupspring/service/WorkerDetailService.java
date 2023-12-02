package com.careerup.careerupspring.service;


import com.careerup.careerupspring.dto.WorkerDetailDTO;
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

    public WorkerDetailDTO workerDetail(String nickname){
        Optional<UserEntity> worker = userRepository.findByNickname(nickname);
        List<String> userSkills = new ArrayList<>();
        List<String> userFields = new ArrayList<>();
//        skillEntity의 skill을 List에 추가
        for (UserSkillEntity userSkill: worker.get().getSkills()){
            userSkills.add(userSkill.getSkill());
        }
//        fieldEntity의 field을 List에 추가
        for (UserFieldEntity userField: worker.get().getFields()){
            userFields.add(userField.getField());
        }

        WorkerDetailDTO workerDetail = WorkerDetailDTO.builder()
                .nickname(worker.get().getNickname())
                .profile(worker.get().getProfile())
                .company(worker.get().getCompany())
                .contents(worker.get().getContents())
                .skills(userSkills)
                .fields(userFields).build();
        return workerDetail;
    }
}
