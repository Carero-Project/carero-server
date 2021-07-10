package com.carero.service;

import com.carero.domain.recruit.RecruitSubCat;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeSubCat;
import com.carero.domain.user.User;
import com.carero.repository.ResumeRepository;
import com.carero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Transactional
    public Long create(Resume resume){
        resumeRepository.save(resume);
        return resume.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        resumeRepository.deleteById(id);

    }

    @Transactional
    public void update(Long id, Resume newResume) {
        Resume origin = resumeRepository.findById(id).get();

        origin.changeInfo(newResume.getCertificationInfo(), newResume.getResumeWantedInfo(), newResume.getEducationInfo(),
                newResume.getCareerInfo(), newResume.getDetailInfo());

        origin.changeTitle(newResume.getTitle());
        origin.changeNation(newResume.getNation());
        origin.changeIsParent(newResume.getIsParent());
        origin.changeContactTime(newResume.getContactTime());
        origin.updateModifiedDate();

        // cats 테이블에 업데이트
        origin.getSubCats().clear();
        List<ResumeSubCat> cats = newResume.getSubCats();
        for (ResumeSubCat cat: cats) {
            origin.addCat(cat);
        }


    }
}
