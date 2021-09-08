package com.carero.controller;

import com.carero.dto.response.RestResponse;
import com.carero.dto.response.SingleResponse;
import com.carero.service.FileStorageService;
import com.carero.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("http://localhost:8080")
@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {
    private final FileStorageService storageService;
    private final ResponseService responseService;

    @PostMapping("/upload")
    public RestResponse uploadFile(@RequestParam("file") MultipartFile file){
        storageService.storeFile(file);

        return responseService.getSuccessResponse();
    }

}

