package com.carero.controller;

import com.carero.dto.resume.ResumeCUDRequestDto;
import com.carero.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    FileStorageService fileStore;

    @PostMapping()
    public String remainOrder(@RequestPart("recruit") ResumeCUDRequestDto resumeCUDRequestDto, @RequestPart("files") List<MultipartFile> certificates) {
        String firstName = resumeCUDRequestDto.getCertificationInfo().getCertificates().get(0).getName();
        String secondName = resumeCUDRequestDto.getCertificationInfo().getCertificates().get(1).getName();

        System.out.println(firstName + " " + secondName);
        for (MultipartFile cert :
                certificates) {
            String originalFilename = cert.getOriginalFilename();
            System.out.println(originalFilename);
        }

        return "good";
    }
}
