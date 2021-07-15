package com.carero.dto.recruit;

import com.carero.domain.recruit.WorkInfo;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkInfoDto {
    public WorkInfoDto() {
    }

    @NotEmpty
    private String city;
    @NotEmpty
    private String sigungu;
    private String workWeek;
    private String workType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workStartDate;
    private String workTermDate;
    private String wage;
    private String wageType;
    @NotEmpty
    private Boolean isCctv;
    private String familyInfo;
    private String mainInfo;

    public WorkInfoDto(WorkInfo workInfo) {
        this.city = workInfo.getCity();
        this.sigungu = workInfo.getSigungu();
        this.workWeek = workInfo.getWorkWeek();
        this.workType = workInfo.getWorkType();
        this.workStartDate = workInfo.getWorkStartDate();
        this.workTermDate = workInfo.getWorkTermDate();
        this.wage = workInfo.getWage();
        this.wageType = workInfo.getWageType();
        this.isCctv = workInfo.getIsCctv();
        this.familyInfo = workInfo.getFamilyInfo();
        this.mainInfo = workInfo.getMainInfo();

    }

    public WorkInfo toEntity(){

        WorkInfo workInfo = WorkInfo.builder()
                .city(city)
                .sigungu(sigungu)
                .workWeek(workWeek)
                .workType(workType)
                .workStartDate(workStartDate)
                .workTermDate(workTermDate)
                .wage(wage)
                .wageType(wageType)
                .isCctv(isCctv)
                .familyInfo(familyInfo)
                .mainInfo(mainInfo)
                .build();

        return workInfo;
    }

    @Builder
    public WorkInfoDto(@NotEmpty String city, @NotEmpty String sigungu, String wageType,
                       String workWeek, String workType, LocalDate workStartDate, String workTermDate,String wage, @NotEmpty Boolean isCctv,
                       String familyInfo, String mainInfo) {
        this.city = city;
        this.sigungu = sigungu;
        this.workWeek = workWeek;
        this.workType = workType;
        this.workStartDate = workStartDate;
        this.workTermDate = workTermDate;
        this.wageType = wageType;
        this.wage = wage;
        this.isCctv = isCctv;
        this.familyInfo = familyInfo;
        this.mainInfo = mainInfo;
    }
}
