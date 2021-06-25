package com.carero.domain.recruit;

import com.carero.domain.cat.SubCategory;

import javax.persistence.*;

@Entity
public class RecruitSubCat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_sub_cat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
}
