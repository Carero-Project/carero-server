package com.carero.dto.recruit;

import com.carero.domain.recruit.EtcInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EtcInfoDto {
    public EtcInfoDto() {
    }

    @NotNull
    private Boolean isContract;
    @NotNull
    private Boolean isInsurance;
    private String submitDocument;
    private String interviewFee;

    public EtcInfoDto(EtcInfo etcInfo) {
        this.isContract = etcInfo.getIsContract();
        this.isInsurance = etcInfo.getIsInsurance();
        this.submitDocument = etcInfo.getSubmitDocument();
        this.interviewFee = etcInfo.getInterviewFee();
    }

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
