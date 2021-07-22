package com.carero.dto.resume;

import com.carero.domain.resume.WantedPlaceSeq;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WantedPlaceSeqDto {
    private int seq;
    private String city;
    private String sigungu;


    public WantedPlaceSeqDto(int seq, String city, String sigungu) {
        this.seq = seq;
        this.city = city;
        this.sigungu = sigungu;
    }

    public WantedPlaceSeqDto(WantedPlaceSeq wantedPlaceSeq) {
        this.seq = wantedPlaceSeq.getSeq();
        this.city = wantedPlaceSeq.getCity();
        this.sigungu = wantedPlaceSeq.getSigungu();
    }
}
