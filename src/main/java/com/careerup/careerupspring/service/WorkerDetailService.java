package com.careerup.careerupspring.service;


import com.careerup.careerupspring.dto.WorkerDetailDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        UserEntity worker = userRepository.findByNickname(nickname).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));

        List<String> userSkills = new ArrayList<>();
        List<String> userFields = new ArrayList<>();

        // skillEntity의 skill을 List에 추가
        for (UserSkillEntity userSkill: worker.getSkills()){
            userSkills.add(userSkill.getSkill());
        }

        // fieldEntity의 field을 List에 추가
        for (UserFieldEntity userField: worker.getFields()){
            userFields.add(userField.getField());
        }

        WorkerDetailDTO workerDetail = WorkerDetailDTO.builder()
                .nickname(worker.getNickname())
                .profile(worker.getProfile())
                .company(worker.getCompany())
                .contents(worker.getContents())
                .skills(userSkills)
                .fields(userFields).build();

        return workerDetail;
    }
}
