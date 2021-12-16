package com.ktymty.imagemetadatafeature.service;

import com.ktymty.imagemetadatafeature.data.DataSource;
import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.dto.FeaturesResponseDto;
import com.ktymty.imagemetadatafeature.exception.NotFoundException;
import com.ktymty.imagemetadatafeature.mapper.FeatureToFeatureResponseDtoMapper;
import com.ktymty.imagemetadatafeature.model.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {
    private final FeatureToFeatureResponseDtoMapper mapper;
    private final DataSource dataSource;

    public FeaturesResponseDto findAll() {
        log.debug("Returning all features");
        List<FeatureResponseDto> featureResponseDtos = dataSource.getFeatures().stream().map(mapper::convert).collect(Collectors.toList());
        return FeaturesResponseDto.builder().features(featureResponseDtos).build();
    }

    public FeatureResponseDto findById(UUID id) {
        log.debug(format("Returning feature by id={%s}.", id));
        return mapper.convert(getFeatureById(id));
    }

    public byte[] findQuicklookByFeatureId(UUID id) {
        log.debug(format("Returning quicklook for feature by id={%s}.", id));
        return getFeatureById(id).getProperties().getQuicklook();
    }

    private Feature getFeatureById(UUID id) {
        return dataSource.getFeatureById(id)
                .orElseThrow(() -> new NotFoundException(format("Feature with id {%s} not found.", id.toString())));
    }
}
