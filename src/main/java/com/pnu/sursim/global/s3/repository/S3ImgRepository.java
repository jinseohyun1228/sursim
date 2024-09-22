package com.pnu.sursim.global.s3.repository;

import com.pnu.sursim.global.s3.entity.S3Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3ImgRepository extends JpaRepository<S3Img, Long> {
}
