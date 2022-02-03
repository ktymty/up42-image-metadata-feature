package com.ktymty.imagemetadatafeature.service;

import com.ktymty.imagemetadatafeature.data.DataSource;
import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.dto.FeaturesResponseDto;
import com.ktymty.imagemetadatafeature.mapper.FeatureToFeatureResponseCustomConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FeatureServiceImplTest {

    private FeatureService featureService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FeatureToFeatureResponseCustomConverter converter;


    @BeforeEach
    void setUp() {
        featureService = new FeatureServiceImpl(converter, dataSource);
    }

    @Test
    void whenFindAll_returnAllFeatures() {
        FeaturesResponseDto actual = featureService.findAll();
        List<FeatureResponseDto> expected = dataSource.getFeatures().stream().map(converter::convert).collect(Collectors.toList());
        assertThat(actual.getFeatures(), equalTo(expected));
    }

    @Test
    void whenFindById_withValidId_returnOneFeature() {
        UUID uuid = UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655");
        FeatureResponseDto actual = featureService.findById(uuid);
        FeatureResponseDto expected = dataSource.getFeatureById(uuid).map(converter::convert).get();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void whenFindQuicklookByFeatureId_withValidId_returnByteData() {
        UUID uuid = UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
        byte[] actual = featureService.findQuicklookByFeatureId(uuid);
        byte[] expected = dataSource.getFeatureById(uuid).map(f -> f.getProperties().getQuicklook()).get();
        assertThat(actual, equalTo(expected));
    }
}