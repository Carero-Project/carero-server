package com.carero.domain.resume;

import com.carero.domain.cat.SubCategory;

import javax.persistence.*;

@Entity
public class ResumeSubCat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_sub_cat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
}
