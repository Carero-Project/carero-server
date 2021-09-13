package com.carero.repository;

import com.carero.domain.ResumeZzim;
import com.carero.domain.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeZzimRepository extends JpaRepository<ResumeZzim, Long> {
    List<ResumeZzim> findAllByResume(Resume resume);
}
