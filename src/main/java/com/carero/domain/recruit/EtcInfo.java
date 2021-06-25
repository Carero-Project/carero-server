package com.carero.domain.recruit;

import javax.persistence.*;

@Entity
public class EtcInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_info_id")
    private Long id;

    @Column(nullable = false)
    private Boolean isContract;

    @Column(nullable = false)
    private Boolean isInsurance;

    @Column(columnDefinition = "TEXT")
    private String submitDocument;

    private String interviewFee;
}
