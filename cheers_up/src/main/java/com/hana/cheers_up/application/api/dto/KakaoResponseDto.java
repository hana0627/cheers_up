package com.hana.cheers_up.application.api.dto;

import java.util.List;

public record KakaoResponseDto(
        MetaDto metaDto,
        List<DocumentDto> documentDtos
) {

}
