package com.carero.repository;

import com.carero.domain.RecruitZzim;
import com.carero.domain.recruit.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @Query("select r from Recruit r" +
            " join fetch r.user u" +
            " join fetch r.workInfo wi" +
            " order by r.id desc")
    public List<Recruit> findByPage(Pageable pageable);

    @Query("select r from Recruit r" +
            " left outer join fetch r.recruitFiles rf" +
            " left outer join fetch rf.file file" +
            " where r.id = ?1")
    Optional<Recruit> findByIdWithFiles(Long id);

}
