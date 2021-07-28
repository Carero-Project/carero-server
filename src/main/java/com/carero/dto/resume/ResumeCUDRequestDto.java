package com.carero.dto.resume;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.CertificationInfo;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeWantedInfo;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class ResumeCUDRequestDto {


    @NotEmpty
    private Long userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private List<SubCategoryCreateDto> cats;
    @NotEmpty
    private CertificationInfoDto certificationInfo;
    @NotEmpty
    private ResumeWantedInfoDto resumeWantedInfo;
    @NotEmpty
    private String nation;
    private String educationInfo;
    private String careerInfo;
    private Boolean isParent;
    private String contactTime;
    private String detailInfo;

    public ResumeCUDRequestDto(@NotEmpty Long userId, @NotEmpty List<SubCategoryCreateDto> subCats, @NotEmpty CertificationInfoDto certificationInfo,
                               @NotEmpty ResumeWantedInfoDto resumeWantedInfo, @NotEmpty String title, @NotEmpty String nation,
                               String educationInfo, String careerInfo, Boolean isParent, String contactTime, String detailInfo) {
        this.userId = userId;
        this.cats = subCats;
        this.certificationInfo = certificationInfo;
        this.resumeWantedInfo = resumeWantedInfo;
        this.title = title;
        this.nation = nation;
        this.educationInfo = educationInfo;
        this.careerInfo = careerInfo;
        this.isParent = isParent;
        this.contactTime = contactTime;
        this.detailInfo = detailInfo;
    }

    public Resume createResume(User user, List<SubCategory> subCats) {
        CertificationInfo certificationInfo = this.certificationInfo.toEntity();

        ResumeWantedInfo resumeWantedInfo = this.resumeWantedInfo.toEntity();

        Resume resume = Resume.builder()
                .user(user)
                .subCats(subCats)
                .certificationInfo(certificationInfo)
                .resumeWantedInfo(resumeWantedInfo)
                .title(title)
                .nation(nation)
                .educationInfo(educationInfo)
                .careerInfo(careerInfo)
                .isParent(isParent)
                .contactTime(contactTime)
                .detailInfo(detailInfo)
                .build();

        return resume;
    }
}
