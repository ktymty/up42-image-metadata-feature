package com.ktymty.imagemetadatafeature.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDetails {
    Date timestamp;
    HttpStatus error;
    String message;
    String details;
}
