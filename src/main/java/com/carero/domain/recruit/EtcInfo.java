package com.carero.domain.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public EtcInfo(Boolean isContract, Boolean isInsurance, String submitDocument, String interviewFee) {
        this.isContract = isContract;
        this.isInsurance = isInsurance;
        this.submitDocument = submitDocument;
        this.interviewFee = interviewFee;
    }
}
