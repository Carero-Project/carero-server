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

    private String workWeek;
    private String workType;
    private LocalDate workStartDate;
    private String workTermDate;

    @Column(length = 2)
    private String wageType;
    private String wage;

    @Column(nullable = false)
    private Boolean isCctv;

    private String familyInfo;

    @Column(columnDefinition = "TEXT")
    private String mainInfo;

    @Builder
    public WorkInfo(String city, String sigungu, String workWeek, String workType, String wageType,
                    LocalDate workStartDate, String workTermDate, String wage, Boolean isCctv, String familyInfo, String mainInfo) {
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
