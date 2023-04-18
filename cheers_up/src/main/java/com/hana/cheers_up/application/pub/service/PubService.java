package com.hana.cheers_up.application.pub.service;

import com.hana.cheers_up.application.pub.repository.PubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PubService {

    private final PubRepository pubRepository;
}
