package com.ktymty.imagemetadatafeature.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeatureResponseDto {
    @NotNull UUID id;
    Long timestamp;
    Long beginViewingDate;
    Long endViewingDate;
    String missionName;
}
