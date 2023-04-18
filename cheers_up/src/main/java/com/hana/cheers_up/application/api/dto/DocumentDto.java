package com.hana.cheers_up.application.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record DocumentDto(
        @JsonProperty("place_name")
        String placeName,
        @JsonProperty("address_name")
        String addressName,
        @JsonProperty("y")
        double latitude,
        @JsonProperty("x")
        double longitude,
        @JsonProperty("distance")
        double distance
) {


}
