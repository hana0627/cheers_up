package com.hana.cheers_up.application.user.controller;

import com.hana.cheers_up.application.user.dto.UserAccountDto;
import com.hana.cheers_up.application.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/user/add")
    public String addUser(UserAccountDto dto) {
        log.info("[UserAccountController addUser] ");
        userAccountService.saveUser(dto);
        return "redirect:/";
    }
}
