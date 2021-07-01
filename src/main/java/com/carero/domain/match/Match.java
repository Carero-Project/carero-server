package com.carero.domain.match;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.resume.Resume;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(nullable = false)
    private Boolean isPaid;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private LocalDateTime matchDate;
}
