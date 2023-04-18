package com.hana.cheers_up.application.pub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {
    // 최대 20개 까지 노출
    private static final int MAX_SEARCH_COUNT = 20;
    // 5키로 내외 
    // TODO: 결고값이 넉넉하다면 1km반경으로 줄여보자
    private static final double RADIUS_KM = 5.0;



}
