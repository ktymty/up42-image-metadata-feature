package com.ktymty.imagemetadatafeature.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ktymty.imagemetadatafeature.exception.InternalServerErrorException;
import com.ktymty.imagemetadatafeature.model.Feature;
import com.ktymty.imagemetadatafeature.model.FeatureCollection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class DataSource {

    private static final String sourceDataFileName = "source-data.json";

    private List<FeatureCollection> featureCollection;

    public DataSource() {
        loadSourceData();
    }

    private void loadSourceData() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            featureCollection = Arrays.asList(
                    mapper.readValue(getClass().getClassLoader().getResourceAsStream(sourceDataFileName),
                            FeatureCollection[].class)
            );
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new InternalServerErrorException(format("Input source data file {%s} cannot be parsed.", sourceDataFileName));
        }
    }

    public List<Feature> getFeatures() {
        return featureCollection.stream()
                .flatMap(featureCollection -> Arrays.stream(featureCollection.getFeatures()))
                .collect(Collectors.toList());
    }

    public Optional<Feature> getFeatureById(UUID id) {
        return getFeatures().stream().filter(f -> f.getProperties().getId().equals(id)).findAny();
    }
}
