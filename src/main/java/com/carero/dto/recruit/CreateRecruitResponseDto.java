package com.carero.dto.recruit;

import lombok.Data;

@Data
public class CreateRecruitResponseDto {
    public CreateRecruitResponseDto() {
    }

    private Long id;

    public CreateRecruitResponseDto(Long id) {
        this.id = id;
    }
}
