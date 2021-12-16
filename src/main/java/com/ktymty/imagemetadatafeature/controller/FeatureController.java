package com.ktymty.imagemetadatafeature.controller;

import com.ktymty.imagemetadatafeature.dto.FeatureResponseDto;
import com.ktymty.imagemetadatafeature.dto.FeaturesResponseDto;
import com.ktymty.imagemetadatafeature.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "feature controller", description = "feature controller for image metadata api")
public class FeatureController {
    private final FeatureService featureService;

//    @Operation(summary = "Return all features")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Features returned successfully", response = String.class)
//    })

    @Operation(summary = "Return all features")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature returned successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FeaturesResponseDto.class))})})
    @GetMapping(value = "/features", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FeaturesResponseDto> getFeatures() {
        return ResponseEntity.ok().body(featureService.findAll());
    }

//    @ApiOperation(value = "Return a feature by id")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Feature returned successfully", response = FeatureResponseDto.class),
//            @ApiResponse(code = 404, message = "Feature not found.")
//    })

    @Operation(summary = "Return a feature by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature returned successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FeatureResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Feature not found.", content = @Content)})
    @GetMapping(value = "/features/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureResponseDto> getFeature(@Parameter(description = "id of feature to be searched") @PathVariable("id") final UUID featureId) {
        return ResponseEntity.ok().body(featureService.findById(featureId));
    }

//    @ApiOperation(value = "Return a quick look image by feature id")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Image returned successfully", response = byte[].class),
//            @ApiResponse(code = 404, message = "Feature not found.")
//    })

    @Operation(summary = "Return a feature by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image returned successfully", content = {
                    @Content(mediaType = "image/png", schema = @Schema(implementation = byte[].class))}),
            @ApiResponse(responseCode = "404", description = "Feature not found.", content = @Content)})
    @GetMapping(value = "/features/{id}/quicklook", produces = IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQuickLook(@Parameter(description = "id of image to be quick looked") @PathVariable("id") final UUID featureId) {

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(featureService.findQuicklookByFeatureId(featureId));
    }
}
