package com.carero.controller;

import com.carero.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {
    private final FileStorageService fileStorageService;

//    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {

        File file = new File(fileStorageService.getFullPath(fileName));
        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", Files.probeContentType(file.toPath()));
        InputStream in = new FileInputStream(file);

        return new ResponseEntity<>(IOUtils.toByteArray(in), header, HttpStatus.OK);

    }

}

