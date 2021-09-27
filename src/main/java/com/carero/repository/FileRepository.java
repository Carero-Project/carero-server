package com.carero.repository;

import com.carero.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<UploadFile, Long> {

    Optional<UploadFile> findByFileName(String fileName);
}
