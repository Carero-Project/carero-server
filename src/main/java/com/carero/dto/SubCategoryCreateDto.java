package com.carero.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubCategoryCreateDto {
    private Long id;

    public SubCategoryCreateDto(Long id) {
        this.id = id;
    }
}
