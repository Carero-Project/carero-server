package com.carero.service;

import com.carero.advice.exception.NoSuchRecruitException;
import com.carero.advice.exception.NoSuchZzimException;
import com.carero.advice.exception.NotMultiZzimException;
import com.carero.domain.FileDescType;
import com.carero.domain.RecruitZzim;
import com.carero.domain.UploadFile;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.recruit.RecruitFile;
import com.carero.domain.recruit.RecruitSubCat;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.recruit.RecruitCUDRequestDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.repository.RecruitRepository;
import com.carero.repository.RecruitZzimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final RecruitZzimRepository recruitZzimRepository;
    private final SubCatService subCatService;

    private final FileStorageService fileStorageService;

    @Value("${file.url}")
    private String fileBaseUrl;

    @Transactional
    public Long create(Recruit recruit) {
        recruitRepository.save(recruit);
        return recruit.getId();
    }

    @Transactional
    public Long create(User user, RecruitCUDRequestDto recruitDto, MultipartFile thumbnail) {
        List<SubCategoryCreateDto> catIds = recruitDto.getCats();
        List<SubCategory> subCats = getSubCategories(catIds);

        Recruit recruit = recruitDto.createRecruit(user, subCats);
        RecruitFile thumbRecruitFile = saveRecruitFile(thumbnail, FileDescType.THUMBNAIL);
        if (thumbRecruitFile != null) {
            recruit.addFile(thumbRecruitFile);
        }

        recruitRepository.save(recruit);

        return recruit.getId();
    }

    private RecruitFile saveRecruitFile(MultipartFile file, FileDescType descType) {
        if (file != null && !file.isEmpty()) {
            UploadFile savedFile = fileStorageService.storeFile(file);

            return new RecruitFile(savedFile, descType);
        }
        return null;
    }

    public Recruit findById(Long recruitId) {
        return recruitRepository.findById(recruitId).orElseThrow(NoSuchRecruitException::new);
    }

    public List<Recruit> findAll() {
        return recruitRepository.findAll();
    }

    @Transactional
    public void deleteAll() {
        recruitRepository.deleteAll();
    }

    @Transactional
    public void delete(User user, Long id) {
        // 만약 RecruitFile로 서버에 저장된 파일이 존재한다면? 삭제
        Recruit targetRecruit = recruitRepository.findByIdWithFiles(id).orElseThrow(NoSuchRecruitException::new);

        if (!Objects.equals(user.getId(), targetRecruit.getUser().getId())) {
            throw new AuthorizationServiceException("해당 글을 수정할 권한이 없습니다.");
        }
        List<RecruitFile> recruitFiles = targetRecruit.getRecruitFiles();
        List<String> fileNames = recruitFiles.stream()
                .map(recruitFile -> recruitFile.getFile().getFileName())
                .collect(Collectors.toList());
        fileStorageService.deleteByFileNames(fileNames);

        // 찜 삭제
        List<RecruitZzim> zzimIncludingTarget = recruitZzimRepository.findAllByRecruit(targetRecruit);
        recruitZzimRepository.deleteAll(zzimIncludingTarget);

        recruitRepository.deleteById(id);
    }

    @Transactional
    public void update(User user, Long id, RecruitCUDRequestDto recruitDto, MultipartFile newThumbnailFile) {
        Recruit origin = recruitRepository.findById(id).orElseThrow(NoSuchRecruitException::new);

        if (!Objects.equals(user.getId(), origin.getUser().getId())) {
            throw new AuthorizationServiceException("해당 글을 수정할 권한이 없습니다.");
        }

        List<SubCategory> subCats = getSubCategories(recruitDto.getCats());
        Recruit newRecruit = recruitDto.createRecruit(user, subCats);

        origin.changeInfo(newRecruit.getWorkInfo(), newRecruit.getTargetInfo(), newRecruit.getWantedInfo(), newRecruit.getEtcInfo());
        origin.changeTitle(newRecruit.getTitle());
        origin.updateModifiedDate();

        // 파일 수정
        List<RecruitFile> recruitFiles = origin.getRecruitFiles();

        if (recruitFiles.size() > 0) {
            List<String> fileNames = recruitFiles.stream()
                    .map(resumeFile -> resumeFile.getFile().getFileName())
                    .collect(Collectors.toList());

            fileStorageService.deleteByFileNames(fileNames);
            origin.getRecruitFiles().clear();
        }

        if (newThumbnailFile != null) {
            RecruitFile newThumbnail = saveRecruitFile(newThumbnailFile, FileDescType.THUMBNAIL);
            if (newThumbnail != null) {
                origin.addFile(newThumbnail);
            }
        }

        // cats 테이블에 업데이트
        origin.getSubCats().clear();
        List<RecruitSubCat> cats = newRecruit.getSubCats();
        for (RecruitSubCat cat : cats) {
            origin.addCat(cat);
        }

    }

    public List<RecruitReadDto> findByPage(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Recruit> recruits = recruitRepository.findByPage(pageable);

        List<RecruitReadDto> recruitReadDtos = new ArrayList<>();

        recruits.forEach(recruit -> {
            RecruitReadDto recruitReadDto = new RecruitReadDto(recruit);

            RecruitFile thumbnailFile = getThumbFilesInRecruitFile(recruit);

            if (thumbnailFile != null) {
                String thumbFileName = thumbnailFile.getFile().getFileName();

                String thumbFileUrl = fileStorageService.getUrl(thumbFileName);
                recruitReadDto.attachThumbnail(thumbFileUrl);
            }

            recruitReadDtos.add(recruitReadDto);
        });

        return recruitReadDtos;

    }

    public long countAll() {
        return recruitRepository.count();
    }

    @Transactional
    public void zzim(User user, Recruit recruit) {
        RecruitZzim savedZzim = recruitZzimRepository.findByUserAndRecruit(user, recruit).orElse(null);
        if (savedZzim == null) {
            RecruitZzim recruitZzim = new RecruitZzim(user, recruit);
            recruitZzimRepository.save(recruitZzim);
        } else {
            throw new NotMultiZzimException();
        }
    }

    @Transactional
    public void deleteZzim(User user, Recruit recruit) {
        RecruitZzim recruitZzim = recruitZzimRepository.findByUserAndRecruit(user, recruit).orElseThrow(NoSuchZzimException::new);
        recruitZzimRepository.delete(recruitZzim);
    }

    private List<SubCategory> getSubCategories(List<SubCategoryCreateDto> catIds) {
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }


    public RecruitReadDto findByIdWithThumbnail(Long id) {
        Recruit recruit = recruitRepository.findByIdWithFiles(id).orElseThrow(NoSuchRecruitException::new);

        RecruitReadDto recruitReadDto = new RecruitReadDto(recruit);

        RecruitFile thumbnailFile = getThumbFilesInRecruitFile(recruit);

        if (thumbnailFile != null) {
            String thumbFileName = thumbnailFile.getFile().getFileName();

            String thumbFileUrl = fileStorageService.getUrl(thumbFileName);
            recruitReadDto.attachThumbnail(thumbFileUrl);
        }

        return recruitReadDto;
    }

    private RecruitFile getThumbFilesInRecruitFile(Recruit recruit) {
        return recruit.getRecruitFiles().stream()
                .filter(file -> file.getDesc().equals(FileDescType.THUMBNAIL) && !file.getFile().getDeleted())
                .findFirst().orElse(null);
    }
}
