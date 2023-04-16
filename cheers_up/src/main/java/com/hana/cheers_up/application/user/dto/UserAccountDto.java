package com.hana.cheers_up.application.user.dto;

import com.hana.cheers_up.application.user.domain.UserAccount;
import com.hana.cheers_up.application.user.domain.constant.RoleType;

public record UserAccountDto(
        String userId,
        String password,
        String email,
        String nickname,
        String memo,
        RoleType roleType
) {


    private static UserAccountDto of(String userId, String password, String email, String nickname, String memo, RoleType roleType) {
        return new UserAccountDto(userId, password, email, nickname, memo, roleType);
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                password,
                email,
                nickname,
                memo,
                roleType
        );
    }
}
