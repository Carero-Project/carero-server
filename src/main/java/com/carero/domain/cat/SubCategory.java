package com.carero.domain.cat;

import javax.persistence.*;

@Entity
public class SubCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long id;

    private String subCategoryName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parentCategory;
}
