package com.carero.repository;

import com.carero.domain.RecruitZzim;
import com.carero.domain.ResumeZzim;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeZzimRepository extends JpaRepository<ResumeZzim, Long> {
    List<ResumeZzim> findAllByResume(Resume resume);

    Optional<ResumeZzim> findByUserAndResume(User user, Resume resume);
}
