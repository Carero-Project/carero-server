package com.carero.domain.resume;

import com.carero.domain.FileDescType;
import com.carero.domain.UploadFile;
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

    public ResumeFile(UploadFile file, FileDescType desc){
        this.resume = null;
        this.file = file;
        this.desc = desc;
    }

    public void connectResume(Resume resume){
        this.resume = resume;
    }
}
