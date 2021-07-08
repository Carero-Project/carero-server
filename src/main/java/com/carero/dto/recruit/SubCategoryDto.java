package com.carero.dto.recruit;

import lombok.Data;

@Data
public class SubCategoryDto {
    public SubCategoryDto() {
    }

    private String subCatName;

    public SubCategoryDto(String subCatName) {
        this.subCatName = subCatName;
    }
}
