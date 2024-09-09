package com.pnu.sursim.global.auth.service;

import com.pnu.sursim.domain.user.dto.UserVo;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.auth.dto.KakaoToken;
import com.pnu.sursim.global.auth.dto.KakaoUser;
import com.pnu.sursim.global.auth.jwt.JWTUtil;
import com.pnu.sursim.global.auth.properties.KakaoProperties;
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

    public String loginKakao(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", kakaoProperties.authorizationCode());
        map.add("client_id", kakaoProperties.clientId());
        map.add("redirect_uri", kakaoProperties.redirectUri());
        map.add("code", code);

        KakaoToken kakaoToken = restClient.post()
                .uri(kakaoProperties.tokenUri())
                .contentType(FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(KakaoToken.class)
                .getBody();

        assert kakaoToken != null;
        KakaoUser kakaoUser = restClient.post()
                .uri(kakaoProperties.userInfoUri())
                .contentType(FORM_URLENCODED)
                .header(kakaoProperties.authorization(), BEARER + kakaoToken.accessToken())
                .retrieve()
                .toEntity(KakaoUser.class)
                .getBody();


        assert kakaoUser != null;
        User savedUser = userRepository.findByEmail(kakaoUser.email())
                //해당 값이 null일때만
                .orElseGet(()->userRepository.save(new User(kakaoUser)));


        String token = jwtUtil.createToken(new UserVo(savedUser));
        System.out.println("token = " + token);

        return token;
    }
}
