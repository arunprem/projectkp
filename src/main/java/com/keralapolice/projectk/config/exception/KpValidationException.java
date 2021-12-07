package com.keralapolice.projectk.config.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class KpValidationException extends Exception {

    String field;
    String validation;



    public KpValidationException(String message, String field) {

        this.field = field;
        this.validation = validation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
