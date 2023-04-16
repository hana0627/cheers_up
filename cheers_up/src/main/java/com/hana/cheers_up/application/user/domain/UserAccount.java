package com.hana.cheers_up.application.user.domain;

import com.hana.cheers_up.application.user.domain.constant.RoleType;
import com.hana.cheers_up.application.user.domain.constant.RoleTypeConvertor;
import lombok.Getter;

import javax.persistence.*;

@Entity
public class UserAccount extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id값
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

    public Long getId() {
        return id;
    }

    public UserAccount(Long id, String password, String email, String nickname, String memo, RoleType roleType) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.roleType = roleType;
    }

    public static UserAccount of(Long id, String password, String email, String nickname, String memo, RoleType roleType) {
        return new UserAccount(id, password, email, nickname, memo, roleType);
    }
}
