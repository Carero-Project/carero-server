package com.carero.repository;

import com.carero.domain.resume.ResumeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeFileRepository extends JpaRepository<ResumeFile, Long> {
}
