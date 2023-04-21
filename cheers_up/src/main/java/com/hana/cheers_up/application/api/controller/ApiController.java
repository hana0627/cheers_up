package com.hana.cheers_up.application.api.controller;

import com.hana.cheers_up.application.api.service.KakaoSearchService;
import com.hana.cheers_up.application.pub.dto.response.PubResponse;
import com.hana.cheers_up.application.pub.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ApiController {

    private final KakaoSearchService kakaoSearchService;
    private final DirectionService directionService;
    @PostMapping("/search")
    public String CheersSearch(String address) {
        log.info("[CheersController CheersSearch] - called");

        List<PubResponse> pubResponses = directionService.recommendPubs(address);
        log.info("결과값 확인");
        pubResponses.stream().forEach(
                System.out::println
        );
        return "cheers/pub_list";
    }
}
