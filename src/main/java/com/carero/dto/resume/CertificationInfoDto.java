package com.carero.dto.resume;

import com.carero.domain.resume.Certificate;
import com.carero.domain.resume.CertificationInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CertificationInfoDto {

    private List<@Valid CertificateDto> certificates;

    @NotNull
    private Boolean agreeCctv;

    @NotNull
    private Boolean isInsurance;

    @NotNull
    private Boolean haveCrimeCert;

    public CertificationInfoDto(CertificationInfo certificationInfo) {
        this.certificates = certificationInfo.getCertificates().stream()
                .map(certificate -> new CertificateDto(certificate)).collect(Collectors.toList());
        this.agreeCctv = certificationInfo.getAgreeCctv();
        this.isInsurance = certificationInfo.getIsInsurance();
        this.haveCrimeCert = certificationInfo.getHaveCrimeCert();

    }

    @Builder
    public CertificationInfoDto(List<CertificateDto> certificates, @NotEmpty Boolean agreeCctv, @NotEmpty Boolean isInsurance, @NotEmpty Boolean haveCertificate, @NotEmpty Boolean haveCrimeCert) {
        this.certificates = certificates;
        this.agreeCctv = agreeCctv;
        this.isInsurance = isInsurance;
        this.haveCrimeCert = haveCrimeCert;
    }

    public CertificationInfo toEntity() {

        // 자격증이 없을 때
        List<Certificate> certificatesList;

        if (this.certificates != null) {
            certificatesList = this.certificates
                    .stream()
                    .map(c -> new Certificate(c.getName(), c.getAcquisitionDate()))
                    .collect(Collectors.toList());
        } else {
            certificatesList = null;
        }

        CertificationInfo certificationInfo = CertificationInfo.builder()
                .certificates(certificatesList)
                .agreeCctv(agreeCctv)
                .isInsurance(isInsurance)
                .haveCrimeCert(haveCrimeCert)
                .build();

        return certificationInfo;
    }
}
