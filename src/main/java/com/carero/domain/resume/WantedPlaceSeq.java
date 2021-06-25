package com.carero.domain.resume;

import javax.persistence.*;

@Entity
public class WantedPlaceSeq {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wanted_place_seq_id")
    private Long id;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String sigungu;
    @Column(nullable = false)
    private String eupmyeondong;
    @Column(nullable = false)
    private int seq;

    @ManyToOne
    @JoinColumn(name = "resume_wanted_info_id")
    private ResumeWantedInfo resumeWantedInfo;
}
