package com.carero.dto.recruit;

import lombok.Data;

@Data
public class RecruitResponseDto {
    public RecruitResponseDto() {
    }

    private Long id;

    public RecruitResponseDto(Long id) {
        this.id = id;
    }
}
