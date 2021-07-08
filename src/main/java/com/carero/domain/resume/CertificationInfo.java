package com.carero.domain.resume;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CertificationInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_info_id")
    private Long id;

    @OneToMany(mappedBy = "certificationInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates = new ArrayList<>();

    @Column(nullable = false)
    private Boolean agreeCctv;

    @Column(nullable = false)
    private Boolean isInsurance;

    @Column(nullable = false)
    private Boolean haveCertificate;

    @Column(nullable = false)
    private Boolean haveCrimeCert;
}
