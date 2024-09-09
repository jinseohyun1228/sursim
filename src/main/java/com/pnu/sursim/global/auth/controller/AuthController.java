package com.pnu.sursim.global.auth.controller;


import com.pnu.sursim.domain.user.dto.JoinRequest;
import com.pnu.sursim.domain.user.dto.LoginRequest;
import com.pnu.sursim.global.auth.service.KakaoService;
import com.pnu.sursim.global.auth.service.NativeService;
import com.pnu.sursim.global.auth.util.CookieUtil;
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
    public void showKakaoLoginScreen(HttpServletResponse response) throws IOException {
        String url = kakaoService.getKakaoLoginUrl();
        response.sendRedirect(url);
    }

    @GetMapping("/auth/login/kakao/callback")
    public void loginKakao(@RequestParam("code") String code, HttpServletResponse response){
        String token = kakaoService.loginKakao(code);
        response.addCookie(CookieUtil.createCookie(token));
    }

    @PostMapping("/auth/join")
    public ResponseEntity<?> joinUser(@RequestBody JoinRequest joinRequest) {
        nativeService.joinUser(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Membership registration completed successfully");
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> joinUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = nativeService.loginUser(loginRequest);
        response.addCookie(CookieUtil.createCookie(token));
        return ResponseEntity.status(HttpStatus.OK)
                .body("Login completed successfully");
    }

}
