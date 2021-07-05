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

    public WorkInfo toEntity(){

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

        return workInfo;
    }

    @Builder
    public WorkInfoDto(@NotEmpty String city, @NotEmpty String sigungu, @NotEmpty String eupmyeondong,
                       String workWeek, String workType, LocalDate workStartDate, String workTermDate,
                       LocalTime workingStartHour, LocalTime workingEndHour, String wage, @NotEmpty Boolean isCctv,
                       String familyInfo, String petInfo, String mainInfo) {
        this.city = city;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.workWeek = workWeek;
        this.workType = workType;
        this.workStartDate = workStartDate;
        this.workTermDate = workTermDate;
        this.workingStartHour = workingStartHour;
        this.workingEndHour = workingEndHour;
        this.wage = wage;
        this.isCctv = isCctv;
        this.familyInfo = familyInfo;
        this.petInfo = petInfo;
        this.mainInfo = mainInfo;
    }
}
