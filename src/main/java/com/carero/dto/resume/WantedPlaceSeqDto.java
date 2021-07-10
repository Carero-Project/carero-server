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
    private String eupmyeondong;

    public WantedPlaceSeqDto(int seq, String city, String sigungu, String eupmyeondong) {
        this.seq = seq;
        this.city = city;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
    }

}
