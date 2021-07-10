package com.carero.domain.resume;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate acquisitionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_info_id")
    private CertificationInfo certificationInfo;

    public void connectCertificationInfo(CertificationInfo certificationInfo){
        this.certificationInfo = certificationInfo;
    }

    public Certificate(String name, LocalDate acquisitionDate) {
        this.name = name;
        this.acquisitionDate = acquisitionDate;
    }
}
