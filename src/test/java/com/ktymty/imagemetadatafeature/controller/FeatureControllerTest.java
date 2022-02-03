package com.ktymty.imagemetadatafeature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.dto.FeaturesResponseDto;
import com.ktymty.imagemetadatafeature.service.FeatureService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("FeatureController")
class FeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeatureService featureService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("should return list of features")
    void whenGetFeatures_returnsHTTP200_and_features() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/features")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        FeaturesResponseDto features = mapper.readValue(result.getResponse().getContentAsString(), FeaturesResponseDto.class);
        assertThat(features.getFeatures().size(), is(14));

        List<FeatureResponseDto> actual = features.getFeatures();
        FeaturesResponseDto expected = featureService.findAll();

        assertThat(actual.stream().anyMatch(f -> !expected.getFeatures().contains(f)), is(false));
        assertThat(expected.getFeatures().stream().anyMatch(f -> !actual.contains(f)), is(false));

    }

    @Test
    @DisplayName("should return a feature when given a valid feature uuid")
    void whenGetFeature_returnsHTTP200_and_feature() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/features/{id}", "ca81d759-0b8c-4b3f-a00a-0908a3ddd655")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        FeatureResponseDto feature = mapper.readValue(result.getResponse().getContentAsString(), FeatureResponseDto.class);
        assertThat(feature, notNullValue());

        assertThat(feature.getBeginViewingDate(), equalTo(1558155123786L));
        assertThat(feature.getEndViewingDate(), is(1558155148785L));
        assertThat(feature.getTimestamp(), is(1558155123786L));
        assertThat(feature.getMissionName(), is("Sentinel-1A"));
    }

    @Test
    @DisplayName("should respond with HTTP404 when feature id is not found")
    void whenGetFeature_withInvalidFeatureId_returnsHTTP404() throws Exception {
        mockMvc.perform(get("/v1/features/{id}", "ca81d759-1111-aaaa-2222-0908a3ddd655")
                        .accept("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("should return with a image response when given a valid feature id")
    void whenGetQuickLook_withValidFeatureId_returnsHTTP200_and_byteImage() throws Exception {
        String uuid = "ca81d759-0b8c-4b3f-a00a-0908a3ddd655";
        MvcResult result = mockMvc.perform(get("/v1/features/{id}/quicklook", uuid)
                        .accept("image/png"))
                .andExpect(status().isOk())
                .andReturn();

        byte[] actual = result.getResponse().getContentAsByteArray();
        byte[] expected = featureService.findQuicklookByFeatureId(UUID.fromString(uuid));

        assertThat(actual, notNullValue());
        assertThat(actual.length, is(expected.length));
        assertThat(actual, equalTo(expected));
    }

    @Test
    @DisplayName("should respond with HTTP404 when requested with an invalid feature id")
    void whenGetQuickLook_withInvalidFeatureId_returnsHTTP404() throws Exception {
        mockMvc.perform(get("/v1/features/{id}/quicklook", "ca81d759-1111-aaaa-2222-0908a3ddd655"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}