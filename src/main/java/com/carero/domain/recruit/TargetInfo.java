package com.carero.domain.recruit;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class TargetInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "target_info_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private int targetAge;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String remark;

    private String carePlace;

    private String species;

}
