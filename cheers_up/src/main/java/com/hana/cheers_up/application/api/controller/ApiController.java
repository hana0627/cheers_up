package com.hana.cheers_up.application.api.controller;

import com.hana.cheers_up.application.api.service.KakaoSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ApiController {

    private final KakaoSearchService kakaoSearchService;
    @PostMapping("/search")
    public String CheersSearch(String address) {
        log.info("[CheersController CheersSearch] - called");

        kakaoSearchService.requestAddressSearch(address);
        return "cheers/search";
    }
}
