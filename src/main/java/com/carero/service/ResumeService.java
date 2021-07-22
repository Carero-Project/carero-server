package com.carero.service;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.recruit.RecruitSubCat;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeSubCat;
import com.carero.domain.user.User;
import com.carero.dto.resume.ResumePageDto;
import com.carero.dto.resume.ResumeReadDto;
import com.carero.repository.ResumeRepository;
import com.carero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Resume findById(Long id) {

        return resumeRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    public List<ResumePageDto> findByPage(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Resume> resumes = resumeRepository.findByPage(pageable);

        List<ResumePageDto> resumeDtos = resumes.stream()
                .map(r -> new ResumePageDto(r))
                .collect(Collectors.toList());

        return resumeDtos;
    }

    public long countAll() {
        return resumeRepository.count();
    }
}
