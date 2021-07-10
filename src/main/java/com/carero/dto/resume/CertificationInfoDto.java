package com.carero.dto.resume;

import com.carero.domain.resume.Certificate;
import com.carero.domain.resume.CertificationInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CertificationInfoDto {

    private List<CertificateDto> certificates;

    @NotEmpty
    private Boolean agreeCctv;

    @NotEmpty
    private Boolean isInsurance;

    @NotEmpty
    private Boolean haveCrimeCert;


    @Builder
    public CertificationInfoDto(List<CertificateDto> certificates, @NotEmpty Boolean agreeCctv, @NotEmpty Boolean isInsurance, @NotEmpty Boolean haveCertificate, @NotEmpty Boolean haveCrimeCert) {
        this.certificates = certificates;
        this.agreeCctv = agreeCctv;
        this.isInsurance = isInsurance;
        this.haveCrimeCert = haveCrimeCert;
    }

    public CertificationInfo toEntity(){
        List<Certificate> certificates = this.certificates
                .stream()
                .map(c -> new Certificate(c.getName(), c.getAcquisitionDate()))
                .collect(Collectors.toList());

        CertificationInfo certificationInfo = CertificationInfo.builder()
                .certificates(certificates)
                .agreeCctv(agreeCctv)
                .isInsurance(isInsurance)
                .haveCrimeCert(haveCrimeCert)
                .build();

        return certificationInfo;
    }
}
