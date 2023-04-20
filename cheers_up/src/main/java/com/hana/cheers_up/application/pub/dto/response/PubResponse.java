package com.hana.cheers_up.application.pub.dto.response;

import com.hana.cheers_up.application.pub.domain.Direction;
import lombok.Builder;

public record PubResponse(
        String pubName,
        String pubAddress,
        String directionUrl,
        String roadViewUrl,
        String roadView,
        String distance
) {

    public static PubResponse from(Direction direction) {
        return PubResponse.builder()
                .pubName(direction.getTargetPubName())
                .pubAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }


    @Builder
    public PubResponse(String pubName, String pubAddress, String directionUrl, String roadViewUrl, String roadView, String distance) {
        this.pubName = pubName;
        this.pubAddress = pubAddress;
        this.directionUrl = directionUrl;
        this.roadViewUrl = roadViewUrl;
        this.roadView = roadView;
        this.distance = distance;
    }
}
