package com.jsproject.youtubeclone.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@AllArgsConstructor
@Service
public class S3Service implements FileService{
    private final S3Client s3Client;
    private final String BUCKET_NAME="videos-projet-bucket";
    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();  //Multipart file uploaded on server
        InputStream inputStream = new ByteArrayInputStream(bytes);

        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = String.valueOf(UUID.randomUUID()) + "." + fileExtension;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(BUCKET_NAME)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        if(bytes.length==file.getSize()){
            try {
                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(),file.getSize()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String s3ObjectUrl = "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + key;

        return s3ObjectUrl;
    }
}
