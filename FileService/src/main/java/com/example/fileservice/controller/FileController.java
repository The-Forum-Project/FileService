package com.example.fileservice.controller;

import com.example.fileservice.service.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final S3FileService fileService;

    @Autowired
    public FileController(S3FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
        String uri = fileService.uploadFile(fileName, file);
        return ResponseEntity.ok(uri);
    }

}
