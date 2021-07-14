package com.carero.repository;

import com.carero.domain.resume.Resume;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select r from Resume r" +
            " join fetch r.certificationInfo cert" +
            " join fetch r.resumeWantedInfo rwi" +
            " join fetch r.user u" +
            " order by r.id desc")
    public List<Resume> findByPage(Pageable pageable);
}
