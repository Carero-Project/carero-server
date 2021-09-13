package com.carero.dto.resume;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.CertificationInfo;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeWantedInfo;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ResumeCUDRequestDto {


    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private List<SubCategoryCreateDto> cats;
    private @Valid CertificationInfoDto certificationInfo;
    private @Valid ResumeWantedInfoDto resumeWantedInfo;
    @NotNull
    @NotEmpty
    private String nation;
    @NotNull
    @NotEmpty
    private String educationInfo;
    @NotNull
    @NotEmpty
    private String careerInfo;
    @NotNull
    private Boolean isParent;
    @NotNull
    @NotEmpty
    private String contactTime;
    @NotNull
    @NotEmpty
    private String detailInfo;

    public ResumeCUDRequestDto(@NotEmpty List<SubCategoryCreateDto> subCats, @NotEmpty CertificationInfoDto certificationInfo,
                               @NotEmpty ResumeWantedInfoDto resumeWantedInfo, @NotEmpty String title, @NotEmpty String nation,
                               String educationInfo, String careerInfo, Boolean isParent, String contactTime, String detailInfo) {
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

        return Resume.builder()
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
    }
}
