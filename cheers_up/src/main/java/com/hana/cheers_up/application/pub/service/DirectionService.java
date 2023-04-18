package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.api.dto.DocumentDto;
import com.hana.cheers_up.application.pub.domain.Direction;
import com.hana.cheers_up.application.pub.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
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

    private final PubService pubService;
    private final DirectionRepository directionRepository;

    public List<Direction> DirectionList(DocumentDto documentDto) {
        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return pubService.Pubs().stream()
                .map(
                        pubDto -> Direction.from(documentDto, pubDto)
                ).filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .toList();
    }

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);
    }



}
