package com.pnu.sursim.global.s3.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class S3Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //업로드한 파일 이름
    private String originalFileName;

    //고유한 파일 이름
    @Column(unique = true)
    private String uploadFileName;

    //접근 가능한 URL
    @Column(unique = true)
    private String uploadFileUrl;

    @Builder
    public S3Img(String originalFileName, String uploadFileName, String uploadFileUrl) {
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFileUrl = uploadFileUrl;
    }
}
