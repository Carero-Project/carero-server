package com.carero.domain.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 발행기관
    private String issuer;

    private LocalDate acquisitionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_info_id")
    private CertificationInfo certificationInfo;

    public void connectCertificationInfo(CertificationInfo certificationInfo){
        this.certificationInfo = certificationInfo;
    }

    public Certificate(String name, LocalDate acquisitionDate, String issuer) {
        this.name = name;
        this.acquisitionDate = acquisitionDate;
        this.issuer = issuer;
    }
}
