package com.example.fileservice.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

@Service
public class S3FileService {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String region;
    @Autowired
    public S3FileService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String key, MultipartFile file) {
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            System.out.println("key: " + key);
            System.out.println("file name: " + file.getOriginalFilename());
            //PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getOriginalFilename(), tempFile);

            s3Client.putObject(bucketName, key, file.getInputStream(), metadata);
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
            //return s3Client.getUrl(bucketName, tempFile.getAbsolutePath()).toString();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }catch (AmazonServiceException amazonServiceException){
            amazonServiceException.printStackTrace();
        }catch (AmazonClientException amazonClientException){
            amazonClientException.printStackTrace();
        }
        return null;
    }
}
