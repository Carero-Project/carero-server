package com.carero.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.carero.advice.exception.FileDeleteException;
import com.carero.advice.exception.FileStoreException;
import com.carero.domain.UploadFile;
import com.carero.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileStorageService {

    private final S3Service s3Service;
    private final FileRepository fileRepository;

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

        // s3 저장용 메타데이터
        ObjectMetadata objectMetadata = new ObjectMetadata();

        String originFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFilename);
        long size = multipartFile.getSize();
        String ext = extractExt(originFilename);


        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(multipartFile.getContentType());

//        파일 로컬 업로드
//        try {
//            multipartFile.transferTo(new File(getFullPath(storeFileName)));
//        } catch (IOException e) {
//            throw new FileStoreException();
//        }

        try(InputStream inputStream = multipartFile.getInputStream()){
            s3Service.upload(inputStream, objectMetadata, storeFileName);
        } catch (IOException exception) {
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
        if (originalFilename != null) {
            int pos = originalFilename.lastIndexOf(".");
            return originalFilename.substring(pos + 1);
        }

        return null;
    }

    public void deleteByFileNames(List<String> filename) {
        filename.forEach(this::deleteByFileName);
    }

    @Transactional
    public void deleteByFileName(String filename) {
        UploadFile uploadFile = fileRepository.findByFileName(filename).orElseThrow(FileDeleteException::new);

        uploadFile.deleteOn(); // 삭제 체크만 해둠

//        File file = new File(getFullPath(filename));
//
//        if (file.exists()) {
//            if (file.delete()) {
//                log.info("파일 삭제 : " + filename);
//            } else {
//                log.warn("파일 삭제 실패 : " + filename);
//                throw new FileDeleteException();
//            }
//        } else {
//            log.warn("파일이 존재하지않습니다.");
//        }



    }

    public String getUrl(String fileName){
        String url = s3Service.getUrl(fileName);
        log.info(url);
        return url;
    }
}
