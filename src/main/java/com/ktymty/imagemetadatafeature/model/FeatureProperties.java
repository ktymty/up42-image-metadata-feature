package com.ktymty.imagemetadatafeature.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureProperties {
    @NotNull
    private UUID id;
    private Long timestamp;
    private FeatureAcquisitionProperties acquisition;
    private byte[] quicklook;
}
