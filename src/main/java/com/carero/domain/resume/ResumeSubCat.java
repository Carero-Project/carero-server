package com.carero.domain.resume;

import com.carero.domain.cat.SubCategory;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ResumeSubCat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_sub_cat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
}
