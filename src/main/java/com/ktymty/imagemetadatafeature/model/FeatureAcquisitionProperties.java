package com.ktymty.imagemetadatafeature.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureAcquisitionProperties {
    Long beginViewingDate;
    Long endViewingDate;
    String missionName;
}
