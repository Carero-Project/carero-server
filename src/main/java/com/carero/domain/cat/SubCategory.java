package com.carero.domain.cat;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SubCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long id;

    private String subCategoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category parentCategory;
}
