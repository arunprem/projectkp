package com.keralapolice.projectk.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@RestController
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleApiRequestException(Exception e, WebRequest request){
        // create payload containing exception and details
        HttpStatus badRequest = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
        ApiException apiException = new ApiException(e.getLocalizedMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")));
        //return the resonse entity
        return  new ResponseEntity<>(apiException, badRequest);

    }





}
