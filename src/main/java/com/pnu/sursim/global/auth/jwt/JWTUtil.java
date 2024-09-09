package com.pnu.sursim.global.auth.jwt;

import com.pnu.sursim.domain.user.dto.UserVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(UserVo user) {
        String token = Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발생시간
                .setExpiration(new Date(System.currentTimeMillis() + 2400000L)) // 소멸시간 셋팅
                .signWith(secretKey)
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        return !isExpired(token);

    }

    public String getName(String token) {
        return Jwts.parser()//파서 생성
                .verifyWith(secretKey)
                .build()//파서 키 설정과 빌드 완료
                .parseSignedClaims(token)//토큰 서명 확인
                .getPayload()
                .get("name", String.class);
    }

    public String getEmail(String token) {
        return Jwts.parser()//파서 생성
                .verifyWith(secretKey)
                .build()//파서 키 설정과 빌드 완료
                .parseSignedClaims(token)//토큰 서명 확인
                .getPayload()
                .get("email", String.class);
    }

    private Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }
}
