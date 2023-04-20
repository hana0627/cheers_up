package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.api.dto.DocumentDto;
import com.hana.cheers_up.application.api.dto.KakaoResponseDto;
import com.hana.cheers_up.application.api.service.KakaoSearchService;
import com.hana.cheers_up.application.pub.domain.Direction;
import com.hana.cheers_up.application.pub.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DirectionService {
    // 최대 20개 까지 노출
    private static final int MAX_SEARCH_COUNT = 20;
    // 5키로 내외 
    // TODO: 결고값이 넉넉하다면 1km반경으로 줄여보자
    private static final double RADIUS_KM = 5.0;
    private final DirectionRepository directionRepository;
    private final KakaoSearchService kakaoSearchService;



    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);
    }

    @Transactional
    public void recommendPubs(String address) {
        KakaoResponseDto kakaoResponseDto = kakaoSearchService.requestAddressSearch(address);

        if (ObjectUtils.isEmpty(kakaoResponseDto) || CollectionUtils.isEmpty(kakaoResponseDto.documentDtos())) {
            log.error("[PubService recommendPubs fail] Input address: {}", address);
        }

        DocumentDto documentDto = kakaoResponseDto.documentDtos().get(0);
        List<Direction> directions = buildDirectionListByCategory(documentDto);

        this.saveAll(directions);

    }


    private List<Direction> buildDirectionListByCategory(DocumentDto inputDto) {
        if(Objects.isNull(inputDto)) return Collections.emptyList();

        return kakaoSearchService.requestPubCategorySearch(inputDto.latitude(), inputDto.longitude(),RADIUS_KM)
                .documentDtos().stream()
                .map(pubDto -> Direction.from(inputDto, pubDto))
                .toList();
    }

}
