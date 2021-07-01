package com.carero.domain.resume;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class ResumeWantedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_wanted_info_id")
    private Long id;

    private String wantedWeek;
    private LocalDateTime wantedStartDate;
    private String wantedWorkType;
    private String wantedDuration;
    private LocalTime wantedStartHour;
    private LocalTime wantedEndHour;
    private String wantedWage;

    @Column(length = 2)
    private String wantedWageType;
}
