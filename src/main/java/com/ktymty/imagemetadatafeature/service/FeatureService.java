package com.ktymty.imagemetadatafeature.service;

import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.dto.FeaturesResponseDto;

import java.util.UUID;

public interface FeatureService {
    FeaturesResponseDto findAll();

    FeatureResponseDto findById(UUID id);

    byte[] findQuicklookByFeatureId(UUID id);
}
