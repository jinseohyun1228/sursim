package com.pnu.sursim.global.auth.service;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.domain.user.dto.KakaoFirstInfo;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.entity.UserInfoStatus;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.auth.dto.AuthStatus;
import com.pnu.sursim.global.auth.dto.KakaoToken;
import com.pnu.sursim.global.auth.dto.KakaoUser;
import com.pnu.sursim.global.auth.jwt.JWTUtil;
import com.pnu.sursim.global.auth.properties.KakaoProperties;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import static io.jsonwebtoken.lang.Strings.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
public class KakaoService {

    private static final MediaType FORM_URLENCODED = new MediaType(APPLICATION_FORM_URLENCODED, UTF_8);
    private static final String BEARER = "Bearer ";
    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public KakaoService(KakaoProperties kakaoProperties, UserRepository userRepository, JWTUtil jwtUtil) {
        this.kakaoProperties = kakaoProperties;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.restClient = RestClient.create();
    }


    public String getKakaoLoginUrl() {
        return kakaoProperties.codeUri() +
                "?client_id=" + kakaoProperties.clientId() +
                "&redirect_uri=" + kakaoProperties.redirectUri() +
                "&response_type=code";
    }

    public KakaoToken retrieveKakaoToken(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", kakaoProperties.authorizationCode());
        map.add("client_id", kakaoProperties.clientId());
        map.add("redirect_uri", kakaoProperties.redirectUri());
        map.add("code", code);

        return restClient.post()
                .uri(kakaoProperties.tokenUri())
                .contentType(FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(KakaoToken.class)
                .getBody();

    }

    public AuthStatus loginUserUsingKakaoToken(KakaoToken kakaoToken) {

        KakaoUser kakaoUser = restClient.post()
                .uri(kakaoProperties.userInfoUri())
                .contentType(FORM_URLENCODED)
                .header(kakaoProperties.authorization(), BEARER + kakaoToken.accessToken())
                .retrieve()
                .toEntity(KakaoUser.class)
                .getBody();

        if (kakaoUser == null) {
            throw new CustomException(ErrorCode.KAKAO_LOGIN_ERROR_NO_USER);
        }

        User savedUser = userRepository.findByEmail(kakaoUser.email())
                .orElseGet(() -> userRepository.save(new User(kakaoUser)));

        String jwtToken = jwtUtil.createToken(savedUser.getName(), savedUser.getEmail());

        return new AuthStatus(jwtToken, savedUser.getUserInfoStatus());
    }

    public UserInfoStatus registerUserInfoFirst(AuthUser authUser, KakaoFirstInfo kakaoFirstInfo) {
        User targetUser = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        targetUser.registerUserInfoFirst(kakaoFirstInfo.birthDate(), kakaoFirstInfo.gender(), kakaoFirstInfo.region());

        User savedUser = userRepository.save(targetUser);

        return savedUser.getUserInfoStatus();
    }
}
