package com.carero.domain.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ResumeWantedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_wanted_info_id")
    private Long id;

    @OneToMany(mappedBy = "resumeWantedInfo")
    private List<WantedPlaceSeq> wantedPlaceSeqs;

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
