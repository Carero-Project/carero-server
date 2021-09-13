package com.carero.repository;

import com.carero.domain.resume.Resume;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select r from Resume r" +
            " join fetch r.certificationInfo cert" +
            " join fetch r.resumeWantedInfo rwi" +
            " join fetch r.user u" +
            " left outer join fetch r.resumeFiles rf" +
            " left outer join fetch rf.file f" +
            " order by r.id desc")
    List<Resume> findByPage(Pageable pageable);

//    MultipleBagFetchException 발생으로 XXXTOMANY로 받는 것은 하나만 받을 수 있다.
//    https://www.baeldung.com/java-hibernate-multiplebagfetchexception
    @Query("select distinct r from Resume r" +
            " join fetch r.subCats sc" +
            " join fetch sc.subCategory" +
            " join fetch r.certificationInfo ci" +
            " join fetch r.resumeWantedInfo rwi" +
            " join fetch r.user u" +
            " where r.id = ?1")
    Optional<Resume> findByIdWithThumbnail(Long id);
}
