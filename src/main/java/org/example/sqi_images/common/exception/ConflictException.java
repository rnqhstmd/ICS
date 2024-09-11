package org.example.sqi_images.common.exception;

import org.example.sqi_images.common.exception.type.ErrorType;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {
    public ConflictException(final ErrorType errorType) {
        super(errorType, HttpStatus.CONFLICT);
    }

    public ConflictException(final ErrorType errorType, final String detail) {
        super(errorType, detail, HttpStatus.CONFLICT);
    }
}
