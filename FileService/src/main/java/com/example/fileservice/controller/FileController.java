package com.example.fileservice.controller;

import com.example.fileservice.dto.FileUrlResponse;
import com.example.fileservice.exception.InvalidAuthorityException;
import com.example.fileservice.service.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private final S3FileService fileService;

    @Autowired
    public FileController(S3FileService fileService) {
        this.fileService = fileService;
    }

    //支持上传多个attachments
    @PostMapping
    public ResponseEntity<FileUrlResponse> uploadFile(@RequestParam("files") MultipartFile[] files) throws InvalidAuthorityException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        if (authorities.stream().noneMatch(authority -> authority.getAuthority().equals("normal"))){
            throw new InvalidAuthorityException();
        }else{
            List<String> urls = new ArrayList<>();
            for(MultipartFile file : files){
                String uri = fileService.uploadFile(file);
                urls.add(uri);
            }
            return ResponseEntity.ok(FileUrlResponse.builder().statusCode("200").urls(urls).build());
        }
    }

}
