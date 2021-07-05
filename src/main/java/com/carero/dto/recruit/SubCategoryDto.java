package com.carero.dto.recruit;

import lombok.Data;

@Data
public class SubCategoryDto {
    private Long id;

    public SubCategoryDto() {
    }

    public SubCategoryDto(Long id) {
        this.id = id;
    }
}
