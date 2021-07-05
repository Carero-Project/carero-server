package com.carero.dto.recruit;

import com.carero.domain.recruit.EtcInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EtcInfoDto {
    public EtcInfoDto() {
    }

    @NotEmpty
    private Boolean isContract;
    @NotEmpty
    private Boolean isInsurance;
    private String submitDocument;
    private String interviewFee;

    public EtcInfo toEntity(){
        EtcInfo etcInfo = EtcInfo.builder()
                .isContract(isContract)
                .isInsurance(isInsurance)
                .submitDocument(submitDocument)
                .interviewFee(interviewFee)
                .build();
        return etcInfo;
    }

    @Builder
    public EtcInfoDto(@NotEmpty Boolean isContract, @NotEmpty Boolean isInsurance, String submitDocument, String interviewFee) {
        this.isContract = isContract;
        this.isInsurance = isInsurance;
        this.submitDocument = submitDocument;
        this.interviewFee = interviewFee;
    }
}
