package com.carero.service;

import com.carero.advice.exception.CertNumException;
import com.carero.advice.exception.NoSuchResumeException;
import com.carero.advice.exception.NoSuchZzimException;
import com.carero.domain.*;
import com.carero.domain.ResumeZzim;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeSubCat;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.resume.ResumeCUDRequestDto;
import com.carero.dto.resume.ResumePageDto;
import com.carero.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeZzimRepository resumeZzimRepository;
    private final SubCatService subCatService;

    // 파일관련
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final ResumeFileRepository resumeFileRepository;

    @Transactional
    public Long create(Resume resume){
        resumeRepository.save(resume);
        return resume.getId();
    }

    @Transactional
    public Long create(User user, ResumeCUDRequestDto resumeDto, MultipartFile thumbnail){
        // resume 생성 저장
        List<SubCategory> subCats = getSubCategories(resumeDto);

        Resume resume = resumeDto.createResume(user, subCats);
        resumeRepository.save(resume);

        saveResumeFile(resume, thumbnail,FileDescType.THUMBNAIL);

        return resume.getId();

    }


//    자격증 외 Resume 관련 파일을 서버에 저장하고 DB에도 저장
    private void saveResumeFile(Resume resume, MultipartFile file, FileDescType descType) {
        if (file != null) {
            UploadFile crimeFile = fileStorageService.storeFile(file);
//            fileRepository.save(crimeFile);
            resumeFileRepository.save(new ResumeFile(resume, crimeFile, descType));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        resumeRepository.deleteById(id);

    }

    @Transactional
    public void update(Long id, Resume newResume) {
        Resume origin = resumeRepository.findById(id).orElseThrow(NoSuchResumeException::new);

        origin.changeInfo(newResume.getCertificationInfo(), newResume.getResumeWantedInfo(), newResume.getEducationInfo(),
                newResume.getCareerInfo(), newResume.getDetailInfo());

        origin.changeTitle(newResume.getTitle());
        origin.changeNation(newResume.getNation());
        origin.changeIsParent(newResume.getIsParent());
        origin.changeContactTime(newResume.getContactTime());
        origin.updateModifiedDate();

        // cats 테이블에 업데이트
        origin.getSubCats().clear();
        List<ResumeSubCat> cats = newResume.getSubCats();
        for (ResumeSubCat cat: cats) {
            origin.addCat(cat);
        }


    }

    public Resume findById(Long id) {
        return resumeRepository.findById(id).orElseThrow(NoSuchResumeException::new);
    }

    public List<ResumePageDto> findByPage(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Resume> resumes = resumeRepository.findByPage(pageable);

        List<ResumePageDto> resumeDtos = resumes.stream()
                .map(r -> new ResumePageDto(r))
                .collect(Collectors.toList());

        return resumeDtos;
    }

    public long countAll() {
        return resumeRepository.count();
    }

    @Transactional
    public Long zzim(User user, Resume resume){
        ResumeZzim resumeZzim = new ResumeZzim(user, resume);
        resumeZzimRepository.save(resumeZzim);
        return resumeZzim.getId();
    }

    @Transactional
    public void deleteZzim(Long zzimId){
        ResumeZzim resumeZzim = resumeZzimRepository.findById(zzimId).orElseThrow(NoSuchZzimException::new);
        resumeZzimRepository.delete(resumeZzim);
    }

    public ResumeZzim findZzimById(Long zzimId){
        return resumeZzimRepository.findById(zzimId).orElseThrow(NoSuchZzimException::new);
    }

    private List<SubCategory> getSubCategories(ResumeCUDRequestDto resumeCUDRequestDto) {
        List<SubCategoryCreateDto> catIds = resumeCUDRequestDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }
}
