package com.carero.domain.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ResumeWantedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_wanted_info_id")
    private Long id;

    @OneToMany(mappedBy = "resumeWantedInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WantedPlaceSeq> wantedPlaceSeqs = new ArrayList<>();

    private String wantedWeek;
    private LocalDate wantedStartDate;
    private String wantedWorkType;
    private String wantedDuration;
    private String wantedWage;

    @Column(length = 2)
    private String wantedWageType;

    @Builder
    public ResumeWantedInfo(List<WantedPlaceSeq> wantedPlaceSeqs, String wantedWeek, LocalDate wantedStartDate,
                            String wantedWorkType, String wantedDuration, String wantedWage, String wantedWageType) {
        this.wantedWeek = wantedWeek;
        this.wantedStartDate = wantedStartDate;
        this.wantedWorkType = wantedWorkType;
        this.wantedDuration = wantedDuration;
        this.wantedWage = wantedWage;
        this.wantedWageType = wantedWageType;
        this.wantedPlaceSeqs = wantedPlaceSeqs;

        for (WantedPlaceSeq wantedPlaceSeq : wantedPlaceSeqs) {
            wantedPlaceSeq.connectResumeWantedInfo(this);
        }
    }
}
