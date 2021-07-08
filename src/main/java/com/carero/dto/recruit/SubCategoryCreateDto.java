package com.carero.dto.recruit;

import lombok.Data;

@Data
public class SubCategoryCreateDto {
    private Long id;

    public SubCategoryCreateDto() {
    }

    public SubCategoryCreateDto(Long id) {
        this.id = id;
    }
}
