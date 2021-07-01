package com.carero.domain.recruit;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
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

}
