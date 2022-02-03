package com.ktymty.imagemetadatafeature.mapper;

import com.ktymty.imagemetadatafeature.data.DataSource;
import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.model.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("FeatureToFeatureResponseCustomConverter")
class FeatureToFeatureResponseCustomConverterTest {
    @Autowired
    private DataSource dataSource;

    private FeatureToFeatureResponseCustomConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FeatureToFeatureResponseCustomConverter();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenConvert_map_returnFeatureResponseDto() {
        UUID uuid = UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655");

        FeatureResponseDto expected = FeatureResponseDto.builder().id(uuid).timestamp(1558155123786L).beginViewingDate(1558155123786L).endViewingDate(1558155148785L).missionName("Sentinel-1A").build();
        Optional<Feature> actualFeatureDto = dataSource.getFeatureById(uuid);

        FeatureResponseDto actual = converter.convert(actualFeatureDto.get());

        assertThat(actual, equalTo(expected));
    }
}