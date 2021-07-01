package com.carero.service;

import com.carero.domain.recruit.Recruit;
import com.carero.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;

    @Transactional
    public Long create(Recruit recruit){
        recruitRepository.save(recruit);
        return recruit.getId();
    }

    public Recruit findOne(Long recruitId){
        return recruitRepository.findOne(recruitId);
    }

    public List<Recruit> findAll(){
        return recruitRepository.findAll();
    }
}
