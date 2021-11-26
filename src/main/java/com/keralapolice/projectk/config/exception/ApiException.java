package com.keralapolice.projectk.config.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final String msg;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String msg, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.msg = msg;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
