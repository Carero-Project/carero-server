package com.carero.dto.recruit;

import com.carero.domain.recruit.Recruit;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitPageDto {
    private Long id;

    private String title;

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


        recruit.getSubCats().stream().forEach(c -> c.getSubCategory().getSubCategoryName());
        this.cat = recruit.getSubCats().get(0).getSubCategory().getParentCategory().getCategoryName();
        recruit.getSubCats().stream().forEach(c -> this.subCats.add(c.getSubCategory().getSubCategoryName()));


    }
}
