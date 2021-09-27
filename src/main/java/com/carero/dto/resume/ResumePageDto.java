package com.carero.dto.resume;

import com.carero.domain.FileDescType;
import com.carero.domain.resume.Resume;
import com.carero.domain.resume.ResumeFile;
import com.carero.dto.recruit.WorkInfoDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResumePageDto {
    private Long id;

    private String title;

    private String thumbnail;

    private String username;

    private String cat;

    private List<String> subCats = new ArrayList<>();

    private String city;

    private String sigungu;

    private Boolean status;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int viewCount;

    private String wageType;
    private String wage;

    private LocalDate wantedStartDate;

    private String workWeek;

    public ResumePageDto(Resume resume,String fileBaseUrl) {
        this.id = resume.getId();
        this.title = resume.getTitle();

        this.username = resume.getUser().getUsername();
        this.city = resume.getUser().getCity();
        this.sigungu = resume.getUser().getSigungu();

        this.createdDate = resume.getCreateDate();
        this.modifiedDate = resume.getModifiedDate();
        this.status = resume.getStatus();
        this.viewCount = resume.getViewCount();

        ResumeWantedInfoDto resumeWantedInfoDto = new ResumeWantedInfoDto(resume.getResumeWantedInfo());
        this.wage = resumeWantedInfoDto.getWantedWage();
        this.wageType = resumeWantedInfoDto.getWantedWageType();
        this.wantedStartDate = resumeWantedInfoDto.getWantedStartDate();
        this.workWeek = resumeWantedInfoDto.getWantedWeek();

        this.thumbnail = null;

        // 강제 초기화
        resume.getSubCats().forEach(c -> c.getSubCategory().getSubCategoryName());

        // 부모 카테고리 설정
        this.cat = resume.getSubCats().get(0).getSubCategory().getParentCategory().getCategoryName();

        // 자식 카테고리 설정
        resume.getSubCats().forEach(c -> this.subCats.add(c.getSubCategory().getSubCategoryName()));

    }
}
