package com.pnu.sursim.global.auth.controller;


import com.pnu.sursim.domain.user.dto.JoinRequest;
import com.pnu.sursim.domain.user.dto.LoginRequest;
import com.pnu.sursim.domain.user.dto.UserVo;
import com.pnu.sursim.global.auth.dto.AuthStatus;
import com.pnu.sursim.global.auth.dto.KakaoToken;
import com.pnu.sursim.global.auth.dto.KakaoUser;
import com.pnu.sursim.global.auth.service.KakaoService;
import com.pnu.sursim.global.auth.service.NativeService;
import com.pnu.sursim.global.auth.util.CookieUtil;
import com.pnu.sursim.global.response.CustomResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final NativeService nativeService;

    @GetMapping("/auth/login/kakao")
    public CustomResponse loginKakaoUser(@RequestBody KakaoToken kakaoToken,HttpServletResponse response){
        AuthStatus authStatus = kakaoService.loginUserUsingKakaoToken(kakaoToken);
        response.addCookie(CookieUtil.createCookie(authStatus.token()));
        return CustomResponse.success(authStatus.status());
    }

    @PostMapping("/auth/join")
    public void joinNativeUser(@RequestBody JoinRequest joinRequest,HttpServletResponse response) {
        String token = nativeService.joinUser(joinRequest);
        response.addCookie(CookieUtil.createCookie(token));
    }


    @PostMapping("/auth/login")
    public void loginNativeUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = nativeService.loginUser(loginRequest);
        response.addCookie(CookieUtil.createCookie(token));
    }

    //BE에서 테스트를 위해 만든
    @GetMapping("/auth/login/kakao-show")
    public void showKakaoLoginScreen(HttpServletResponse response) throws IOException {
        String url = kakaoService.getKakaoLoginUrl();
        response.sendRedirect(url);
    }

    @GetMapping("/auth/login/kakao-token")
    public CustomResponse offerKakaoToken(@RequestParam("code") String code, HttpServletResponse response){
        KakaoToken kakaoToken = kakaoService.retrieveKakaoToken(code);
        return CustomResponse.success(kakaoToken);
    }

}
