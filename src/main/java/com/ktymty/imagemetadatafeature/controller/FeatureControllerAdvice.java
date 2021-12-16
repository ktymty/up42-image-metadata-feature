package com.ktymty.imagemetadatafeature.controller;

import com.ktymty.imagemetadatafeature.exception.ErrorDetails;
import com.ktymty.imagemetadatafeature.exception.InternalServerErrorException;
import com.ktymty.imagemetadatafeature.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class FeatureControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(new Date()).error(HttpStatus.NOT_FOUND).message(ex.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public final ResponseEntity<ErrorDetails> handleInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(new Date()).error(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
