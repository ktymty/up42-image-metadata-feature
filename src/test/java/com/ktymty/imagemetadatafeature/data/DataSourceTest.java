package com.ktymty.imagemetadatafeature.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ktymty.imagemetadatafeature.model.Feature;
import com.ktymty.imagemetadatafeature.model.FeatureCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("DataSource")
class DataSourceTest {

    private DataSource dataSource;
    private List<FeatureCollection> featureCollections;

    @BeforeEach
    void setUp() {
        dataSource = new DataSource();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String sourceDataFileName = "source-data.json";
        try {
            featureCollections = Arrays.asList(mapper.readValue(getClass().getClassLoader().getResourceAsStream(sourceDataFileName), FeatureCollection[].class));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(format("Input source data file {%s} cannot be parsed.", sourceDataFileName));
        }
    }

    @Test
    void whenGetFeatures_isCalled_returnsWithListOfFeatures() {
        List<Feature> actualFeatures = dataSource.getFeatures();
        List<Feature> expectedFeatures = featureCollections.stream().flatMap(f -> Arrays.stream(f.getFeatures())).collect(Collectors.toList());

        assertThat(actualFeatures, equalTo(expectedFeatures));
    }

    @Test
    void whenGetFeatureById_isCalled_returnsWithOneFeature() {
        UUID uuid = UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655");
        Optional<Feature> actualFeature = dataSource.getFeatureById(uuid);
        Feature expectedFeature = featureCollections.stream().flatMap(f -> Arrays.stream(f.getFeatures())).filter(f -> f.getProperties().getId().equals(uuid)).findFirst().get();

        assertThat(actualFeature.isPresent(), is(true));
        assertThat(actualFeature.get(), equalTo(expectedFeature));
    }

    @Test
    void whenGetFeature_isNotFound_returnsWithNone() {
        UUID uuid = UUID.fromString("ca81d759-1111-aaaa-2222-0908a3ddd655");
        Optional<Feature> actualFeature = dataSource.getFeatureById(uuid);

        assertThat(actualFeature.isPresent(), is(false));
    }
}