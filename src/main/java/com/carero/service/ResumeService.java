package com.carero.service;

import com.carero.domain.resume.Resume;
import com.carero.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
