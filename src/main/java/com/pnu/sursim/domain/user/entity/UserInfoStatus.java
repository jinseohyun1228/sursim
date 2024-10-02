package com.pnu.sursim.domain.user.entity;

public enum UserInfoStatus {
    COMPLETE,  // 정보가 모두 채워진 상태
    INCOMPLETE;

    public static UserInfoStatus fromUser(User user) {
        // 필드들이 모두 null이 아닌지 확인
        if (user.getBirthDate() != null &&
                user.getGender() != null ) {
            return COMPLETE;
        } else {
            return INCOMPLETE;
        }
    }
}
