package com.carero.domain.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WantedPlaceSeq {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wanted_place_seq_id")
    private Long id;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String sigungu;
    @Column(nullable = false)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_wanted_info_id")
    private ResumeWantedInfo resumeWantedInfo;

    public void connectResumeWantedInfo(ResumeWantedInfo resumeWantedInfo){
        this.resumeWantedInfo = resumeWantedInfo;
    }
    public WantedPlaceSeq(String city, String sigungu, int seq) {
        this.city = city;
        this.sigungu = sigungu;
        this.seq = seq;
    }
}
