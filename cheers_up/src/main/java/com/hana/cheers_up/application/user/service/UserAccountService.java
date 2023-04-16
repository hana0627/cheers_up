package com.hana.cheers_up.application.user.service;

import com.hana.cheers_up.application.user.domain.UserAccount;
import com.hana.cheers_up.application.user.dto.UserAccountDto;
import com.hana.cheers_up.application.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;


    @Transactional
    public Long saveUser(UserAccountDto dto) {
        log.info("[UserAccountService saveUser]");
        //TODO : 저장전에 password encode를 수행하자
        return userAccountRepository.save(dto.toEntity()).getId();
    }
}
