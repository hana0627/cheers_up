package com.hana.cheers_up.application.api.controller;

import com.hana.cheers_up.application.api.service.KakaoAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ApiController {

    private final KakaoAddressService kakaoAddressService;
    @PostMapping("/search")
    public String CheersSearch(String address) {
        log.info("[CheersController CheersSearch] - called");

        kakaoAddressService.requestAddressSearch(address);
        return "re";
    }
}
