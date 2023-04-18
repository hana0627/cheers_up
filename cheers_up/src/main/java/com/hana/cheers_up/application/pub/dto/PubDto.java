package com.hana.cheers_up.application.pub.dto;

import com.hana.cheers_up.application.pub.domain.Pub;
import lombok.Builder;

@Builder
public record PubDto(
        Long id,
        String pubName,
        String pubAddress,
        double latitude,
        double longitude
) {

    public static PubDto from(Pub pub) {
        return PubDto.builder().
                id(pub.getId()).
                pubName(pub.getPubName()).
                pubAddress(pub.getPubAddress()).
                latitude(pub.getLatitude()).
                longitude(pub.getLongitude())
                .build();
    }

}
