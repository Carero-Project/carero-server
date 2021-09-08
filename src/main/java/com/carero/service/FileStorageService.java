package com.carero.service;

import com.carero.advice.exception.FileStoreException;
import com.carero.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 여러 파일을 저장하고 파일 객체들을 생성해줌
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // 실제 파일을 저장하고 파일 객체를 생성해줌
    public UploadFile storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFilename);
        long size = multipartFile.getSize();
        String ext = extractExt(originFilename);
        log.info(getFullPath(storeFileName));

        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new FileStoreException();
        }
        return UploadFile.builder()
                .fileName(storeFileName)
                .orgName(originFilename)
                .byteSize(size)
                .type(ext).build();
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자를 떼냄
    private String extractExt(String originalFilename) {
        if(originalFilename != null){
            int pos = originalFilename.lastIndexOf(".");
            return originalFilename.substring(pos + 1);
        }

        return null;
    }

}