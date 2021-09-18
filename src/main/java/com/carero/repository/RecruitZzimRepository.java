package com.carero.repository;

import com.carero.domain.RecruitZzim;
import com.carero.domain.recruit.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitZzimRepository extends JpaRepository<RecruitZzim, Long> {
    List<RecruitZzim> findAllByRecruit(Recruit recruit);
}
