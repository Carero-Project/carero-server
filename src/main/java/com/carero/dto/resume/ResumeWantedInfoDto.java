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
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime wantedStartHour;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime wantedEndHour;
    private String wantedWage;
    private String wantedWageType;

    @Builder
    public ResumeWantedInfoDto(List<WantedPlaceSeqDto> wantedPlaceSeqs, String wantedWeek,
                               LocalDate wantedStartDate, String wantedWorkType, String wantedDuration, LocalTime wantedStartHour,
                               LocalTime wantedEndHour, String wantedWage, String wantedWageType) {
        this.wantedPlaceSeqs = wantedPlaceSeqs;
        this.wantedWeek = wantedWeek;
        this.wantedStartDate = wantedStartDate;
        this.wantedWorkType = wantedWorkType;
        this.wantedDuration = wantedDuration;
        this.wantedStartHour = wantedStartHour;
        this.wantedEndHour = wantedEndHour;
        this.wantedWage = wantedWage;
        this.wantedWageType = wantedWageType;
    }

    public ResumeWantedInfo toEntity(){
        List<WantedPlaceSeq> wantedPlaceSeqs = this.wantedPlaceSeqs
                .stream()
                .map(wps -> new WantedPlaceSeq(wps.getCity(),wps.getSigungu(), wps.getEupmyeondong(), wps.getSeq()))
                .collect(Collectors.toList());

        ResumeWantedInfo resumeWantedInfo = ResumeWantedInfo.builder()
                .wantedPlaceSeqs(wantedPlaceSeqs)
                .wantedWeek(wantedWeek)
                .wantedStartDate(wantedStartDate)
                .wantedWorkType(wantedWorkType)
                .wantedDuration(wantedDuration)
                .wantedStartHour(wantedStartHour)
                .wantedEndHour(wantedEndHour)
                .wantedWage(wantedWage)
                .wantedWageType(wantedWageType)
                .build();

        return resumeWantedInfo;
    }
}
