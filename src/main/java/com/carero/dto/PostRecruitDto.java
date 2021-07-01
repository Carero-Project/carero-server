package com.carero.dto;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PostRecruitDto {
    @NotEmpty
    private Long userId;

    @NotEmpty
    private String title;

    private List<SubCategoryDto> cats;

    // WorkInfo
    @NotEmpty
    private String city;
    @NotEmpty
    private String sigungu;
    @NotEmpty
    private String eupmyeondong;
    private String workWeek;
    private String workType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workStartDate;
    private String workTermDate;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime workingStartHour;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime workingEndHour;
    private String wage;
    @NotEmpty
    private Boolean isCctv;
    private String familyInfo;
    private String petInfo;
    private String mainInfo;

    // TargetInfo
    @NotEmpty
    private int targetAge;
    @NotEmpty
    private String remark;
    private String carePlace;
    private String species;

    // WantedInfo
    private String wantedAge;
    private String wantedGender;
    private String wantedCareer;
    private String wantedEducation;
    private String wantedNationality;
    private String prerequisite;
    private String preferential;

    // EtcInfo
    @NotEmpty
    private Boolean isContract;
    @NotEmpty
    private Boolean isInsurance;
    private String submitDocument;
    private String interviewFee;


    public Recruit toEntity(User user, List<SubCategory> subCats) {

        WorkInfo workInfo = WorkInfo.builder()
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .workWeek(workWeek)
                .workType(workType)
                .workStartDate(workStartDate)
                .workTermDate(workTermDate)
                .workingStartHour(workingStartHour)
                .workingEndHour(workingEndHour)
                .wage(wage)
                .isCctv(isCctv)
                .familyInfo(familyInfo)
                .petInfo(petInfo)
                .mainInfo(mainInfo)
                .build();

        TargetInfo targetInfo = TargetInfo.builder()
                .targetAge(targetAge)
                .carePlace(carePlace)
                .remark(remark)
                .species(species)
                .build();

        WantedInfo wantedInfo = WantedInfo.builder()
                .wantedAge(wantedAge)
                .wantedGender(wantedGender)
                .wantedCareer(wantedCareer)
                .wantedEducation(wantedEducation)
                .wantedNationality(wantedNationality)
                .prerequisite(prerequisite)
                .preferential(preferential)
                .build();

        EtcInfo etcInfo = EtcInfo.builder()
                .isContract(isContract)
                .isInsurance(isInsurance)
                .submitDocument(submitDocument)
                .interviewFee(interviewFee)
                .build();

        Recruit recruit = Recruit.builder()
                .user(user)
                .title(title)
                .etcInfo(etcInfo)
                .targetInfo(targetInfo)
                .wantedInfo(wantedInfo)
                .workInfo(workInfo)
                .subCats(subCats)
                .build();

        return recruit;
    }
}
