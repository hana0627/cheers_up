package com.hana.cheers_up.application.user.service;

import com.hana.cheers_up.application.user.domain.UserAccount;
import com.hana.cheers_up.application.user.dto.UserAccountDto;
import com.hana.cheers_up.application.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAccountDto saveUser(UserAccountDto dto) {
        log.info("[UserAccountService saveUser]");
        System.out.println("dto.toString() => " + dto.toString());
        return UserAccountDto.from(userAccountRepository.save(dto.toEntity()));
    }

    public Optional<UserAccountDto> searchUser(String username) {
        log.info("[UserAccountService searchUser]");
        return userAccountRepository.findById(username).map(UserAccountDto::from);
    }
}
