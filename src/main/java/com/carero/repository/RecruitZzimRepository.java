package com.carero.repository;

import com.carero.domain.RecruitZzim;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitZzimRepository extends JpaRepository<RecruitZzim, Long> {
    List<RecruitZzim> findAllByRecruit(Recruit recruit);

    Optional<RecruitZzim> findByUserAndRecruit(User user, Recruit recruit);
}
