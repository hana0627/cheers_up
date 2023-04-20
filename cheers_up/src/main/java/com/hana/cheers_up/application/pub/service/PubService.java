package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.api.dto.DocumentDto;
import com.hana.cheers_up.application.api.dto.KakaoResponseDto;
import com.hana.cheers_up.application.api.service.KakaoSearchService;
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
    private final KakaoSearchService kakaoAddressSearchService;
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

        DocumentDto documentDto = kakaoResponseDto.documentDtos().get(0);
        List<Direction> directions = directionService.buildDirectionListByCategory(documentDto);

        directionService.saveAll(directions);


        // TODO : 결과값을 보고 필히 리팩토링

//        kakaoResponseDto.documentDtos().stream()
//                .map(directionService::DirectionList)
//                .map(directionService::saveAll);
        
//        for (int i = 0; i<kakaoResponseDto.documentDtos().size(); i++) {
//        List<Direction> directions = directionService.DirectionList(kakaoResponseDto.documentDtos().get(i));
//        directionService.saveAll(directions);
//        }
    }

}
