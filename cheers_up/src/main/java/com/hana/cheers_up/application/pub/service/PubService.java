package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.pub.dto.PubDto;
import com.hana.cheers_up.application.pub.repository.PubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PubService {

    private final PubRepository pubRepository;

    public List<PubDto> Pubs() {
        return pubRepository.findAll().stream()
                .map(PubDto::from).toList();
    }

}
