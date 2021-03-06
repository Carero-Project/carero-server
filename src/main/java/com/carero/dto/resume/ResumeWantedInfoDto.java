package com.carero.dto.resume;

import com.carero.domain.resume.ResumeWantedInfo;
import com.carero.domain.resume.WantedPlaceSeq;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResumeWantedInfoDto {
    private List<WantedPlaceSeqDto> wantedPlaceSeqs;

    private String wantedWeek;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate wantedStartDate;
    private String wantedWorkType;
    private String wantedDuration;
    private String wantedWage;
    private String wantedWageType;

    @Builder
    public ResumeWantedInfoDto(List<WantedPlaceSeqDto> wantedPlaceSeqs, String wantedWeek,
                               LocalDate wantedStartDate, String wantedWorkType, String wantedDuration,String wantedWage, String wantedWageType) {
        this.wantedPlaceSeqs = wantedPlaceSeqs;
        this.wantedWeek = wantedWeek;
        this.wantedStartDate = wantedStartDate;
        this.wantedWorkType = wantedWorkType;
        this.wantedDuration = wantedDuration;
        this.wantedWage = wantedWage;
        this.wantedWageType = wantedWageType;
    }

    public ResumeWantedInfoDto(ResumeWantedInfo resumeWantedInfo) {
        this.wantedPlaceSeqs = resumeWantedInfo.getWantedPlaceSeqs().stream()
                .map(wps -> new WantedPlaceSeqDto(wps)).collect(Collectors.toList());
        this.wantedWeek = resumeWantedInfo.getWantedWeek();
        this.wantedStartDate = resumeWantedInfo.getWantedStartDate();
        this.wantedWorkType = resumeWantedInfo.getWantedWorkType();
        this.wantedDuration = resumeWantedInfo.getWantedDuration();
        this.wantedWage = resumeWantedInfo.getWantedWage();
        this.wantedWageType = resumeWantedInfo.getWantedWageType();
    }

    public ResumeWantedInfo toEntity(){
        List<WantedPlaceSeq> wantedPlaceSeqs = this.wantedPlaceSeqs
                .stream()
                .map(wps -> new WantedPlaceSeq(wps.getCity(),wps.getSigungu(), wps.getSeq()))
                .collect(Collectors.toList());

        ResumeWantedInfo resumeWantedInfo = ResumeWantedInfo.builder()
                .wantedPlaceSeqs(wantedPlaceSeqs)
                .wantedWeek(wantedWeek)
                .wantedStartDate(wantedStartDate)
                .wantedWorkType(wantedWorkType)
                .wantedDuration(wantedDuration)
                .wantedWage(wantedWage)
                .wantedWageType(wantedWageType)
                .build();

        return resumeWantedInfo;
    }
}
