package com.carero.dto.recruit;

import com.carero.domain.recruit.TargetInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TargetInfoDto {
    public TargetInfoDto() {
    }

    @NotEmpty
    private int targetAge;
    @NotEmpty
    private String remark;
    private String carePlace;
    private String species;

    public TargetInfo toEntity(){
        TargetInfo targetInfo = TargetInfo.builder()
                .targetAge(targetAge)
                .carePlace(carePlace)
                .remark(remark)
                .species(species)
                .build();
        return targetInfo;
    }

    @Builder
    public TargetInfoDto(@NotEmpty int targetAge, @NotEmpty String remark, String carePlace, String species) {
        this.targetAge = targetAge;
        this.remark = remark;
        this.carePlace = carePlace;
        this.species = species;
    }

    public TargetInfoDto(TargetInfo targetInfo){
        this.targetAge = targetInfo.getTargetAge();
        this.remark = targetInfo.getRemark();
        this.carePlace = targetInfo.getCarePlace();
        this.species = targetInfo.getSpecies();

    }
}
