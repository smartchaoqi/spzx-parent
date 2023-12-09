package com.aqiu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.aqiu.spzx.manager.properties.MinIoProperties;
import com.aqiu.spzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinIoProperties minIoProperties;

    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 创建一个Minio的客户端对象
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minIoProperties.getEndpointUrl())
                .credentials(minIoProperties.getAccessKey(), minIoProperties.getSecretKey())
                .build();

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoProperties.getBucketName()).build());

        // 如果不存在，那么此时就创建一个新的桶
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoProperties.getBucketName()).build());
        } else {  // 如果存在打印信息
            System.out.println("Bucket '"+minIoProperties.getBucketName()+"' already exists.");
        }
        InputStream inputStream = file.getInputStream();
        String date = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String fileName = date+"/"+ UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(minIoProperties.getBucketName())
                .stream(inputStream, file.getSize(), -1)
                .object(fileName)
                .build();
        minioClient.putObject(putObjectArgs) ;

        // 构建fileUrl
        String fileUrl = minIoProperties.getEndpointUrl()+"/"+minIoProperties.getBucketName()+"/"+ fileName;
        return fileUrl;
    }
}
