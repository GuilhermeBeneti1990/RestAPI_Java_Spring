package com.restspring.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TheFileNotFoundException extends RuntimeException {

    public TheFileNotFoundException(String exception) {
        super(exception);
    }

    public TheFileNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }

}
