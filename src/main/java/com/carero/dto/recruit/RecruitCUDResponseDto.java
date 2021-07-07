package com.carero.dto.recruit;

import lombok.Data;

@Data
public class RecruitCUDResponseDto {
    public RecruitCUDResponseDto() {
    }

    private Long id;

    public RecruitCUDResponseDto(Long id) {
        this.id = id;
    }
}
