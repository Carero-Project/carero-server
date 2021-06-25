package com.carero.domain.recruit;

import javax.persistence.*;

@Entity
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
}
