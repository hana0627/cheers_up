package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.api.dto.DocumentDto;
import com.hana.cheers_up.application.api.dto.KakaoResponseDto;
import com.hana.cheers_up.application.api.service.KakaoAddressService;
import com.hana.cheers_up.application.pub.domain.Direction;
import com.hana.cheers_up.application.pub.dto.PubDto;
import com.hana.cheers_up.application.pub.repository.PubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PubService {

    private final PubRepository pubRepository;
    private final KakaoAddressService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<PubDto> Pubs() {
        return pubRepository.findAll().stream()
                .map(PubDto::from).toList();
    }

    @Transactional
    public void recommendPubs(String address) {
        KakaoResponseDto kakaoResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(ObjectUtils.isEmpty(kakaoResponseDto) || CollectionUtils.isEmpty(kakaoResponseDto.documentDtos())) {
            log.error("[PubService recommendPubs fail] Input address: {}" , address);
        }


        //TODO : 이거 stream 못돌리지는 생각. 돌릴 수 있을것 같은데, 내가 빡대가리여서 못하는거 같음.
        //TODO : 최소한 향상된 for 문 혹은 iterator 사용
        for (int i = 0; i<kakaoResponseDto.documentDtos().size(); i++) {
        List<Direction> directions = directionService.DirectionList(kakaoResponseDto.documentDtos().get(i));
        directionService.saveAll(directions);
        }

        //TODO get(0)이 아니라 전부 다 저장
//        List<Direction> directions = directionService.DirectionList(kakaoResponseDto.documentDtos().get(0));
//        directionService.saveAll(directions);
    }

}

//                .forEach(e -> {
//                    directionService.saveAll(e)
//                });



/*
kakaoResponseDto.documentDtos().stream()
    .map(directionService::DirectionList)
    .flatMap(Collection::stream)
    .forEach(directionService::save);
 */






//        kakaoResponseDto.documentDtos().stream()
//                .map(e -> directionService.DirectionList(e))
//                .map(e -> directionService.saveAll(e))
//                .toList();
