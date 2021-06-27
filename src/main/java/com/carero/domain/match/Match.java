package com.carero.domain.match;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.resume.Resume;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(nullable = false)
    private Boolean isPaid;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private LocalDateTime matchDate;
}
