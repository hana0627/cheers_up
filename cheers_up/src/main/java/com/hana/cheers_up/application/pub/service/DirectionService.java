package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.api.dto.DocumentDto;
import com.hana.cheers_up.application.api.dto.KakaoResponseDto;
import com.hana.cheers_up.application.api.service.KakaoSearchService;
import com.hana.cheers_up.application.pub.domain.Direction;
import com.hana.cheers_up.application.pub.dto.response.PubResponse;
import com.hana.cheers_up.application.pub.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DirectionService {
    // 5키로 내외
    private static final double RADIUS_KM = 5.0;
    private static final String PUB_CATEGORY="술집";
    private final PubService pubService;
    private final DirectionRepository directionRepository;
    private final KakaoSearchService kakaoSearchService;

    public List<Direction> DirectionList(DocumentDto documentDto) {
        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return pubService.Pubs().stream()
                .map(
                        pubDto -> Direction.from(documentDto, pubDto)
                ).filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .toList();
    }

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);
    }

    @Transactional
    public List<PubResponse> recommendPubs(String address) {
        log.info("[DirectionService recommendPubs]");
        KakaoResponseDto kakaoResponseDto = kakaoSearchService.requestAddressSearch(address);
        System.out.println(kakaoResponseDto);
        if (ObjectUtils.isEmpty(kakaoResponseDto) || CollectionUtils.isEmpty(kakaoResponseDto.documentDtos())) {
            log.error("[PubService recommendPubs fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoResponseDto.documentDtos().get(0);
        List<Direction> directions = buildDirectionListByCategory(documentDto);

        return directionRepository.saveAll(directions).stream()
                .map(PubResponse::from)
                .filter(pubResponse -> pubResponse.categoryName().contains(PUB_CATEGORY)).toList();

    }


    private List<Direction> buildDirectionListByCategory(DocumentDto inputDto) {
        if(Objects.isNull(inputDto)) return Collections.emptyList();

        return kakaoSearchService.requestPubCategorySearch(inputDto.latitude(), inputDto.longitude(),RADIUS_KM)
                .documentDtos().stream()
                .map(pubDto -> Direction.from(inputDto, pubDto))
                .toList();
    }

}
