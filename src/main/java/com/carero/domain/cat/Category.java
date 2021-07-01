package com.carero.domain.cat;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String categoryName;
}
