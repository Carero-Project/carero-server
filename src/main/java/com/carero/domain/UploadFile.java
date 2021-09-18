package com.carero.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name ="file")
@Getter
@NoArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, name = "byte_size")
    private Long byteSize;

    @Column(nullable = false, name = "org_name")
    private String orgName;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    public UploadFile(String type, Long byteSize, String orgName, String fileName) {
        this.type = type;
        this.byteSize = byteSize;
        this.orgName = orgName;
        this.fileName = fileName;
        this.createdDate = LocalDateTime.now();
    }

}
