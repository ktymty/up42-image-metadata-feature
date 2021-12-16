package com.ktymty.imagemetadatafeature.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI imageMetadataApi(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI().info(new Info()
                .title("Image Meta Data API")
                .version(appVersion)
                .description("Image Metadata Feature simple backend application that is capable of working with image metadata."));
    }
}
