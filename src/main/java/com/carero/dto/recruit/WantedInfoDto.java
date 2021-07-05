package com.carero.dto.recruit;

import com.carero.domain.recruit.WantedInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class WantedInfoDto {
    public WantedInfoDto() {
    }

    private String wantedAge;
    private String wantedGender;
    private String wantedCareer;
    private String wantedEducation;
    private String wantedNationality;
    private String prerequisite;
    private String preferential;

    public WantedInfo toEntity(){
        WantedInfo wantedInfo = WantedInfo.builder()
                .wantedAge(wantedAge)
                .wantedGender(wantedGender)
                .wantedCareer(wantedCareer)
                .wantedEducation(wantedEducation)
                .wantedNationality(wantedNationality)
                .prerequisite(prerequisite)
                .preferential(preferential)
                .build();
        return wantedInfo;
    }

    @Builder
    public WantedInfoDto(String wantedAge, String wantedGender, String wantedCareer, String wantedEducation,
                         String wantedNationality, String prerequisite, String preferential) {
        this.wantedAge = wantedAge;
        this.wantedGender = wantedGender;
        this.wantedCareer = wantedCareer;
        this.wantedEducation = wantedEducation;
        this.wantedNationality = wantedNationality;
        this.prerequisite = prerequisite;
        this.preferential = preferential;
    }
}
