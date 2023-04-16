package com.hana.cheers_up.application.user.domain;

import com.hana.cheers_up.application.user.domain.constant.RoleType;
import com.hana.cheers_up.application.user.domain.constant.RoleTypeConvertor;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class UserAccount extends AuditingFields {

    @Id
    @Column(length = 50)
    private String userId; //id값
    @Column
    private String password; //비밀번호
    @Column(length = 100)
    private String email; // 이메일
    @Column(length = 100)
    private String nickname; // 닉네임

    @Column(length = 100)
    private String memo; // 메모

    @Convert(converter = RoleTypeConvertor.class)
    private RoleType roleType; // 권한정보

    protected UserAccount() {
    }

    public UserAccount(String userId, String password, String email, String nickname, String memo, RoleType roleType, String createdBy) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.roleType = roleType;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }


    public static UserAccount of(String userId, String password, String email, String nickname, String memo, RoleType roleType) {
        return new UserAccount(userId, password, email, nickname, memo, roleType, null);
    }

    public static UserAccount of(String userId, String password, String email, String nickname, String memo, RoleType roleType, String createdBy) {
        return new UserAccount(userId, password, email, nickname, memo, roleType, createdBy);
    }
}
