package com.carero.domain;

import com.carero.domain.resume.Resume;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ResumeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private UploadFile file;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_desc")
    private FileDescType desc;

    public ResumeFile(Resume resume, UploadFile file, FileDescType desc) {
        this.resume = resume;
        this.file = file;
        this.desc = desc;
    }
}
