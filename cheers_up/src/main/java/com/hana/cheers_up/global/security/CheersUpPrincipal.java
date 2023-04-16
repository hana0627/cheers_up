package com.hana.cheers_up.global.security;

import com.hana.cheers_up.application.user.domain.constant.RoleType;
import com.hana.cheers_up.application.user.dto.UserAccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record CheersUpPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo,
        RoleType roleType
) implements UserDetails {

    //TODO 권한정보 구현하다가 망함.

    public static CheersUpPrincipal of(String username, String password, Collection<? extends GrantedAuthority> authorities, String email, String nickname, String memo, RoleType roleType) {
        return new CheersUpPrincipal(
                username, password, authorities, email, nickname, memo, roleType);
    }

    public static CheersUpPrincipal from(UserAccountDto dto) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleType.USER.getRoleName()));
        authorities.add(new SimpleGrantedAuthority(RoleType.MANAGER.getRoleName()));
        authorities.add(new SimpleGrantedAuthority(RoleType.ADMIN.getRoleName()));
        return CheersUpPrincipal.of(
                dto.userId(),
                dto.password(),
                authorities,
                dto.email(),
                dto.nickname(),
                dto.memo(),
                dto.roleType()
        );
    }


    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo,
                RoleType.USER
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleType.USER.getRoleName()));
        authorities.add(new SimpleGrantedAuthority(RoleType.MANAGER.getRoleName()));
        authorities.add(new SimpleGrantedAuthority(RoleType.ADMIN.getRoleName()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
