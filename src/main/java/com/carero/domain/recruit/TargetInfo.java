package com.carero.domain.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public TargetInfo(int targetAge, String remark, String carePlace, String species) {
        this.targetAge = targetAge;
        this.remark = remark;
        this.carePlace = carePlace;
        this.species = species;
    }
}
