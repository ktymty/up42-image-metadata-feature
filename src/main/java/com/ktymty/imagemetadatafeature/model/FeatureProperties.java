package com.ktymty.imagemetadatafeature.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureProperties {
    @NotNull UUID id;
    Long timestamp;
    FeatureAcquisitionProperties acquisition;
    byte[] quicklook;
}
