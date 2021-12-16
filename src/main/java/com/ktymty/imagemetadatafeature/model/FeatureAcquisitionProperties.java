package com.ktymty.imagemetadatafeature.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureAcquisitionProperties {
    private Long beginViewingDate;
    private Long endViewingDate;
    private String missionName;
}
