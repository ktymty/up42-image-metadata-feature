package com.ktymty.imagemetadatafeature.mapper;

import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.model.Feature;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeatureToFeatureResponseCustomConverter implements Converter<Feature, FeatureResponseDto> {
    @Override
    public FeatureResponseDto convert(Feature feature) {
        return FeatureResponseDto.builder().id(feature.getProperties().getId())
                .timestamp(feature.getProperties().getTimestamp())
                .beginViewingDate(feature.getProperties().getAcquisition().getBeginViewingDate())
                .endViewingDate(feature.getProperties().getAcquisition().getEndViewingDate())
                .missionName(feature.getProperties().getAcquisition().getMissionName())
                .build();
    }
}
