package com.carero.domain.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WantedInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wanted_info_id")
    private Long id;

    private String wantedAge;
    @Column(length = 1)
    private String wantedGender;

    private String wantedCareer;
    private String wantedEducation;
    private String wantedNationality;

    @Column(columnDefinition = "TEXT")
    private String prerequisite;
    @Column(columnDefinition = "TEXT")
    private String preferential;

    @Builder
    public WantedInfo(String wantedAge, String wantedGender, String wantedCareer, String wantedEducation, String wantedNationality, String prerequisite, String preferential) {
        this.wantedAge = wantedAge;
        this.wantedGender = wantedGender;
        this.wantedCareer = wantedCareer;
        this.wantedEducation = wantedEducation;
        this.wantedNationality = wantedNationality;
        this.prerequisite = prerequisite;
        this.preferential = preferential;
    }
}
