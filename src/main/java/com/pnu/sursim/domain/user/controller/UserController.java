package com.pnu.sursim.domain.user.controller;

import com.pnu.sursim.domain.user.dto.*;
import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;
import com.pnu.sursim.domain.user.service.UserService;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/join")
    public ResponseEntity<?> joinUser(@RequestBody JoinRequest joinRequest) {
        Map<String, Object> responseBody = new HashMap<>();
        String token = userService.joinUser(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(responseBody);
    }


    @PostMapping("/users/login")
    public ResponseEntity<?> joinUser(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> responseBody = new HashMap<>();
        String token = userService.loginUser(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(responseBody);

    }


    @GetMapping("/users/profile")
    public ResponseEntity<?> getUserInfo(@SessionUser AuthUser user) {
        UserVo userVo = userService.getUserInfo(user);
        return ResponseEntity.ok(new ProfileResponse(userVo));
    }


    @PatchMapping("/users/profile")
    public ResponseEntity<?> changeProfile(@SessionUser AuthUser user, @RequestBody ProfileRequest profileRequest) {
        UserVo userVo = userService.changeProfile(user,profileRequest);
        return ResponseEntity.ok(new ProfileResponse(userVo));
    }

}
