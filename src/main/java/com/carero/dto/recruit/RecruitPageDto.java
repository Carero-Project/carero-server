package com.carero.dto.recruit;

import com.carero.domain.FileDescType;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.recruit.RecruitFile;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecruitPageDto {
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

    private LocalDate workStartDate;

    private String workWeek;
//    private int rank;


    public RecruitPageDto(Recruit recruit) {
        this.id = recruit.getId();
        this.title = recruit.getTitle();
        this.username = recruit.getUser().getUsername();

        this.createdDate = recruit.getCreatedDate();
        this.modifiedDate = recruit.getModifiedDate();
        this.status = recruit.getStatus();
        this.viewCount = recruit.getViewCount();
        WorkInfoDto workInfoDto = new WorkInfoDto(recruit.getWorkInfo());

        this.city = workInfoDto.getCity();
        this.sigungu = workInfoDto.getSigungu();
        this.workStartDate = workInfoDto.getWorkStartDate();
        this.workWeek = workInfoDto.getWorkWeek();

        this.wage = workInfoDto.getWage();
        this.wageType = workInfoDto.getWageType();

        // 프록시 강제 초기화
        recruit.getSubCats().forEach(c -> c.getSubCategory().getSubCategoryName());

        //카테고리 설정
        this.cat = recruit.getSubCats().get(0).getSubCategory().getParentCategory().getCategoryName();
        recruit.getSubCats().forEach(c -> this.subCats.add(c.getSubCategory().getSubCategoryName()));

        List<RecruitFile> thumbnailRecruitFileList = recruit.getRecruitFiles().stream()
                .filter(file -> file.getDesc().equals(FileDescType.THUMBNAIL))
                .collect(Collectors.toList());

        if (thumbnailRecruitFileList.size() > 0) {
            RecruitFile thumbnailRecruitFile = thumbnailRecruitFileList.get(0);
            this.thumbnail = thumbnailRecruitFile.getFile().getFileName();
        }else{
            this.thumbnail = null;
        }

    }
}
