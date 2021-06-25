package com.carero.domain.resume;

import javax.persistence.*;

@Entity
public class CertificationInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_info_id")
    private Long id;

    @Column(nullable = false)
    private Boolean agreeCctv;

    @Column(nullable = false)
    private Boolean isInsurance;

    @Column(nullable = false)
    private Boolean haveCertificate;

    @Column(nullable = false)
    private Boolean haveCrimeCert;
}
