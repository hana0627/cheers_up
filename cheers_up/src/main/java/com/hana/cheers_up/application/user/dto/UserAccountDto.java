package com.hana.cheers_up.application.user.dto;

import com.hana.cheers_up.application.user.domain.UserAccount;
import com.hana.cheers_up.application.user.domain.constant.RoleType;

public record UserAccountDto(
        Long id,
        String password,
        String email,
        String nickname,
        String memo,
        RoleType roleType
) {


    private static UserAccountDto of(Long id, String password, String email, String nickname, String memo, RoleType roleType) {
        return new UserAccountDto(id, password, email, nickname, memo, roleType);
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                id,
                password,
                email,
                nickname,
                memo,
                roleType
        );
    }
}
