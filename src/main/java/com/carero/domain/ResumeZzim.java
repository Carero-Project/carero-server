package com.carero.domain;

import com.carero.domain.recruit.Recruit;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ResumeZzim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_zzim_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public ResumeZzim(User user, Resume resume) {
        this.user = user;
        this.resume = resume;
    }
}
