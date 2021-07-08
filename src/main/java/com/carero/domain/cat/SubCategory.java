package com.carero.domain.cat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SubCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long id;

    private String subCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category parentCategory;

    @Builder
    public SubCategory(String subCategoryName, Category parentCategory) {
        this.subCategoryName = subCategoryName;
        this.parentCategory = parentCategory;
    }

}
