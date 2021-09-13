package com.carero.dto.resume;

import lombok.Data;

@Data
public class ResumeCUDResponseDto {
    private Long id;

    public ResumeCUDResponseDto(Long id) {
        this.id = id;
    }
}
