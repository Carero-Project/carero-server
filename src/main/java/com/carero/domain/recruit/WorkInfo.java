package com.carero.domain.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class WorkInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_info_id")
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String sigungu;

    @Column(nullable = false)
    private String eupmyeondong;

    private String workWeek;
    private String workType;
    private LocalDate workStartDate;
    private String workTermDate;
    private LocalTime workingStartHour;
    private LocalTime workingEndHour;
    private String wage;

    @Column(nullable = false)
    private Boolean isCctv;

    private String familyInfo;
    private String petInfo;

    @Column(columnDefinition = "TEXT")
    private String mainInfo;

    @Builder
    public WorkInfo(String city, String sigungu, String eupmyeondong, String workWeek, String workType,
                    LocalDate workStartDate, String workTermDate, LocalTime workingStartHour,
                    LocalTime workingEndHour, String wage, Boolean isCctv, String familyInfo,
                    String petInfo, String mainInfo) {
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
