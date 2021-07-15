package com.carero.service;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.recruit.RecruitSubCat;
import com.carero.dto.recruit.RecruitPageDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteAll() {
        recruitRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id){
        recruitRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Recruit newRecruit) {

        // userId 와 일치하는지 삽입 필요
        Recruit origin = recruitRepository.findOne(id);
        origin.changeInfo(newRecruit.getWorkInfo(), newRecruit.getTargetInfo(), newRecruit.getWantedInfo(), newRecruit.getEtcInfo());
        origin.changeTitle(newRecruit.getTitle());
        origin.updateModifiedDate();

        // cats 테이블에 업데이트
        origin.getSubCats().clear();
        List<RecruitSubCat> cats = newRecruit.getSubCats();
        for (RecruitSubCat cat: cats) {
            origin.addCat(cat);
        }

    }

    public List<RecruitPageDto> findByPage(int offset, int limit) {
        List<Recruit> recruits = recruitRepository.findByPage(offset, limit);

        List<RecruitPageDto> result = recruits.stream()
                .map(o -> new RecruitPageDto(o))
                .collect(Collectors.toList());


        return result;

    }

    public int countAll(){
        List<Recruit> recruits = recruitRepository.findAll();
        return recruits.size();
    }
}
