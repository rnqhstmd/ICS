package org.example.sqi_images.common.exception;

import org.example.sqi_images.common.exception.type.ErrorType;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(final ErrorType errorType) {
        super(errorType, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final ErrorType errorType, final String detail) {
        super(errorType, detail, HttpStatus.NOT_FOUND);
    }
}
