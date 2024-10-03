package com.pnu.sursim.global.auth.resolver;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SessionUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override //주어진 메서드의 파라미터가 지원할 수 있는 타입인지 검사하고, 아닐경우 false
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthUser.class)
                && parameter.hasParameterAnnotation(SessionUser.class);
    }


    @Override //넣어줄 반환값을 넣어준다.
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        //request 가 null 인 상황 -> AssertionError 발생
        assert request != null;
        // null 일 수 있는 상황 : 예외적인 상황에서 발생 (잘못된 요청 흐름,비동기 처리 중 오류, Mocking/Test 환경)

        // 세션이 없을 때 새로 생성하지 않도록 설정
        HttpSession session = request.getSession(false);

        // @SessionUser 어노테이션이 붙어 있으면 그 어노테이션 객체를 반환하고, 없으면 null을 반환
        SessionUser sessionUserAnnotation = parameter.getParameterAnnotation(SessionUser.class);

        // 어노테이션의 required 속성을 확인 (어노테이션이 있고, true일 경우)
        boolean isRequired = sessionUserAnnotation != null && sessionUserAnnotation.required(); // 기본값은 true

        // 세션에서 "user" 속성 가져오기
        Object user = (session != null) ? session.getAttribute("user") : null;

        // required가 true인데 user 값이 없으면 예외 발생
        if (isRequired && user == null) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        // required가 false이거나 user가 있는 경우 해당 user 반환
        return user;
    }

}