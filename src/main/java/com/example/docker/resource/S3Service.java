package com.example.docker.resource;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(InputStream inputStream, String fileName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.setContentLength(inputStream.available());
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed");
        }
    }
}
