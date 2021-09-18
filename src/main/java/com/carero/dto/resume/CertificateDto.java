package com.carero.dto.resume;

import com.carero.domain.resume.Certificate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CertificateDto {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate acquisitionDate;

    // 발행기관
    @NotNull
    @NotEmpty
    private String issuer;


    public CertificateDto(Certificate certificate){
        this.name = certificate.getName();
        this.acquisitionDate = certificate.getAcquisitionDate();
        this.issuer = certificate.getIssuer();
    }

    public CertificateDto(String name, LocalDate acquisitionDate, String issuer) {
        this.name = name;
        this.acquisitionDate = acquisitionDate;
        this.issuer = issuer;
    }
}
