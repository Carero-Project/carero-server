package com.carero.domain.recruit;

import com.carero.domain.FileDescType;
import com.carero.domain.UploadFile;
import com.carero.domain.resume.Resume;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RecruitFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "file_id")
    private UploadFile file;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_desc")
    private FileDescType desc;

    public RecruitFile(UploadFile file, FileDescType desc){
        this.recruit = null;
        this.file = file;
        this.desc = desc;
    }

    public void connectRecruit(Recruit recruit){
        this.recruit = recruit;
    }
}
