package com.careerup.careerupspring.service;


import com.careerup.careerupspring.dto.WorkerListDTO;
import com.careerup.careerupspring.entity.UserEntity;
import com.careerup.careerupspring.entity.UserFieldEntity;
import com.careerup.careerupspring.entity.UserSkillEntity;
import com.careerup.careerupspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SearchService {
    @Autowired
    UserRepository userRepository;

    //    재직자 리스트 (페이지 요청, 검색)
    public List<WorkerListDTO> searchByOption(String company, List<String> skills, List<String> fields){
        List<UserEntity> workers = userRepository.searchWorkers(company, skills, fields);
        List<WorkerListDTO> workerList = new ArrayList<>();

        // WorkerListDTO 생성 후, List<WorkerListDTO>에 추가
        for (UserEntity worker : workers) {
            List<String> userSkills = new ArrayList<>();
            List<String> userFields = new ArrayList<>();

            // skillEntity의 skill을 List에 추가
            for (UserSkillEntity userSkill: worker.getSkills()){
                userSkills.add(userSkill.getSkill());
            }

            // fieldEntity의 field를 List에 추가
            for (UserFieldEntity userField: worker.getFields()){
                userFields.add(userField.getField());
            }

            WorkerListDTO workerElem = WorkerListDTO.builder()
                    .nickname(worker.getNickname())
                    .company(worker.getCompany())
                    .skills(userSkills)
                    .fields(userFields)
                    .build();

            workerList.add(workerElem);
        }

        return workerList;
    }

}
