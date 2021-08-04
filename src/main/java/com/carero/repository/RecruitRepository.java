package com.carero.repository;

import com.carero.domain.recruit.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @Query("select r from Recruit r" +
            " join fetch r.user u" +
            " join fetch r.workInfo wi" +
            " order by r.id desc")
    public List<Recruit> findByPage(Pageable pageable);
}
