package com.carero.dto.resume;

import com.carero.domain.resume.CertificationInfo;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeWantedInfo;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResumeReadDto {

    private Long id;
    private String title;
    private String username;
    private String cat;
    private List<String> subCats = new ArrayList<>();
    private CertificationInfoDto certificationInfo;
    private ResumeWantedInfoDto resumeWantedInfo;
    private String nation;
    private String educationInfo;
    private String careerInfo;
    private Boolean isParent;
    private String contactTime;
    private String detailInfo;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Boolean status;
    private int viewCount;

    public ResumeReadDto(Resume resume) {
        this.id=resume.getId();
        this.title=resume.getTitle();
        this.username=resume.getUser().getUsername();
        this.nation=resume.getNation();
        this.educationInfo=resume.getEducationInfo();
        this.careerInfo=resume.getCareerInfo();
        this.isParent=resume.getIsParent();
        this.contactTime=resume.getContactTime();
        this.detailInfo=resume.getDetailInfo();
        this.createDate=resume.getCreateDate();
        this.modifiedDate=resume.getModifiedDate();
        this.status=resume.getStatus();
        this.viewCount=resume.getViewCount();

        resume.getSubCats().stream().forEach(c -> this.subCats.add(c.getSubCategory().getSubCategoryName()));

        this.cat=resume.getSubCats().get(0).getSubCategory().getParentCategory().getCategoryName();

        this.certificationInfo = new CertificationInfoDto(resume.getCertificationInfo());
        this.resumeWantedInfo = new ResumeWantedInfoDto(resume.getResumeWantedInfo());
    }
}
