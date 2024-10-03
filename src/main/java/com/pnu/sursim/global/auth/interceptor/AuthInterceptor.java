package com.pnu.sursim.global.auth.interceptor;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.global.auth.jwt.JWTUtil;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final String BEARER = "Bearer ";
    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String authorization = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            response.sendRedirect("/api/login/kakao");
            throw new CustomException(ErrorCode.TOKEN_EMPTY);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if (authorization == null) {
            response.sendRedirect("/api/login/kakao");
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        String token = authorization;

        AuthUser authUser = new AuthUser(jwtUtil.getName(token), jwtUtil.getEmail(token));

        HttpSession session = request.getSession(true);

        session.setAttribute("user", authUser);
        System.out.println("authUser = " + authUser);
        return true;
    }

    @Override
    //컨트롤러 실행후 (예외 발생안해여!)
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    // 뷰 실행 후 (컨트롤러에서 발생 예외 여기로 전송된다링구 (링구먼지암?ㅋ 링커스 친구들임)
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }
}
