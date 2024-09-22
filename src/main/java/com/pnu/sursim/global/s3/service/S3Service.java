package com.pnu.sursim.global.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import com.pnu.sursim.global.s3.entity.S3Img;
import com.pnu.sursim.global.s3.repository.S3ImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client s3Client;
    private final S3ImgRepository s3ImgRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    //s3으로 파일 업로드
    @Transactional
    public String uploadImg(MultipartFile multipartFile) {
        System.out.println("multipartFile = " + multipartFile);

        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";


        //S3 객체 업로드 시 메타데이터 사용
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {

            // S3에 파일 업로드
            // 외부에서 읽을 수 있도록 하기 위해 withCannedAcl을 설정
            s3Client.putObject(
                    new PutObjectRequest(bucketName, uploadFileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3 Url 반환
            uploadFileUrl = s3Client.getUrl(bucketName, uploadFileName).toString();

        } catch (IOException e) {
            throw new CustomException(ErrorCode.ERROR_UPLOADING_IMAGE);
        }
        S3Img savedS3Image = s3ImgRepository.save(S3Img.builder()
                .originalFileName(originalFileName)
                .uploadFileUrl(uploadFileUrl)
                .uploadFileName(uploadFileName)
                .build());

        return savedS3Image.getUploadFileUrl();
    }

    private String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }


}
