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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DirectionService {
    // 5키로 내외
    private static final double RADIUS_KM = 3.0;
    private static final String PUB_CATEGORY = "술집";
    private final PubService pubService;
    private final DirectionRepository directionRepository;
    private final KakaoSearchService kakaoSearchService;
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    public List<Direction> DirectionList(DocumentDto documentDto) {
        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return pubService.Pubs().stream()
                .map(
                        pubDto -> Direction.from(documentDto, pubDto)
                ).filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .toList();
    }

//    @Transactional
//    public List<Direction> saveAll(List<Direction> directionList) {
//        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
//
//        return directionRepository.saveAll(directionList);
//    }

    @Transactional
    public List<PubResponse> recommendPubs(String address) {
        log.info("[DirectionService recommendPubs]");
        KakaoResponseDto kakaoResponseDto = kakaoSearchService.requestAddressSearch(address);
        if (ObjectUtils.isEmpty(kakaoResponseDto) || CollectionUtils.isEmpty(kakaoResponseDto.documentDtos())) {
            log.error("[PubService recommendPubs fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoResponseDto.documentDtos().get(0);
        List<Direction> directions = buildDirectionListByCategory(documentDto);

//        return directionRepository.saveAll(directions).stream()
        return directions.stream()
                .map(direction -> {
                    String params = String.join(",", direction.getTargetPubName(),
                            String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

                    String directionUrl = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();
                    log.info("direction params: {}, url: {}", params, directionUrl);

                    String roadViewUrl = ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude();

                    return PubResponse.from(direction, directionUrl, roadViewUrl);
                })
                .filter(pubResponse -> pubResponse.categoryName().contains(PUB_CATEGORY)).toList();
    }

    private List<Direction> buildDirectionListByCategory(DocumentDto inputDto) {

        if (Objects.isNull(inputDto)) return Collections.emptyList();
        List<Direction> directions = new ArrayList<>();
        int page = 1;

        KakaoResponseDto responseDto = searchCategory(inputDto, page);

        directions.addAll(responseDto
                .documentDtos().stream()
                .map(pubDto -> Direction.from(inputDto, pubDto))
                .toList());

        while (true) {
            page++;
            responseDto = searchCategory(inputDto, page);
            directions.addAll(responseDto.documentDtos().stream()
                    .map(pubDto -> Direction.from(inputDto, pubDto))
                    .toList());
            //결과값이 끝이거나, 예상외로 결과값이 너무 많을경우 20번만 반복하는것으로 제한 
            if (responseDto.metaDto().isEnd() || page > 20) {
                break;
            }
        }
        return directions;
    }

    private KakaoResponseDto searchCategory(DocumentDto inputDto, int page) {
        return kakaoSearchService.requestPubCategorySearch(inputDto.latitude(), inputDto.longitude(), RADIUS_KM, page);
    }

}
