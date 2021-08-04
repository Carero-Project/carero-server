package com.carero.service;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.recruit.RecruitSubCat;
import com.carero.dto.recruit.RecruitPageDto;
import com.carero.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Recruit findById(Long recruitId){
        return recruitRepository.findById(recruitId).orElseThrow(()-> new IllegalStateException("해당 ID의 Recruit는 없습니다."));
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
        Recruit origin = recruitRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 ID의 Recruit는 없습니다."));
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
        Pageable pageable = PageRequest.of(offset, limit);
        List<Recruit> recruits = recruitRepository.findByPage(pageable);

        List<RecruitPageDto> resultDtos = recruits.stream()
                .map(r -> new RecruitPageDto(r))
                .collect(Collectors.toList());


        return resultDtos;

    }

    public long countAll(){
        return recruitRepository.count();
    }
}
