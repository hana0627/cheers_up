package com.hana.cheers_up.application.user.dto;

import com.hana.cheers_up.application.user.domain.UserAccount;
import com.hana.cheers_up.application.user.domain.constant.RoleType;

import java.time.LocalDateTime;

public record UserAccountDto(
        String userId,
        String email,
        String nickname,
        String memo,
        RoleType roleType,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(String userId, String email, String nickname, String memo, RoleType roleType) {
        return new UserAccountDto(userId, email, nickname, memo, roleType, null, null, null, null);
    }

    public static UserAccountDto of(String userId, String email, String nickname, String memo, RoleType roleType, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, email, nickname, memo, roleType, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return UserAccountDto.of(
                entity.getUserId(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getRoleType(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }


    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                email,
                nickname,
                memo,
                roleType
        );
    }
}
