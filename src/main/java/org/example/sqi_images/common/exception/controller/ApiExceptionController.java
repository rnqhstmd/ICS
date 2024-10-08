package org.example.sqi_images.common.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.sqi_images.common.exception.ApiException;
import org.example.sqi_images.common.exception.dto.ApiExceptionResponse;
import org.example.sqi_images.common.exception.BadRequestException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> customExceptionHandler(final ApiException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(ApiExceptionResponse.res(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        if (fieldError != null) {
            ErrorType errorType = ErrorType.resolveValidationErrorCode(fieldError.getCode());
            String detail = fieldError.getDefaultMessage();
            ApiExceptionResponse response = new ApiExceptionResponse(
                    errorType.getErrorCode(),
                    errorType.getMessage(),
                    detail
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ApiExceptionResponse response = new ApiExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Request validation failed without specific field errors."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {

        BadRequestException badRequestException = new BadRequestException(
                ErrorType.METHOD_NOT_ALLOWED_ERROR, httpRequestMethodNotSupportedException.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiExceptionResponse.res(badRequestException));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiExceptionResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        String detail = "업로드 가능한 파일의 크기는 5MB 미만입니다.";
        ApiExceptionResponse response = new ApiExceptionResponse(
                ErrorType.UPLOAD_FILE_SIZE_LIMIT_EXCEEDED.getErrorCode(),
                ErrorType.UPLOAD_FILE_SIZE_LIMIT_EXCEEDED.getMessage(),
                detail
        );
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiExceptionResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        BadRequestException badRequestException = new BadRequestException(
                ErrorType.MEDIA_TYPE_NOT_ACCEPTABLE_ERROR,
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiExceptionResponse.res(badRequestException));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        BadRequestException badRequestException = new BadRequestException(
                ErrorType.MEDIA_TYPE_NOT_SUPPORTED_ERROR,
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiExceptionResponse.res(badRequestException));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNoResourceFoundException(
            final NoResourceFoundException noResourceFoundException) {

        BadRequestException badRequestException = new BadRequestException(
                ErrorType.NO_RESOURCE_ERROR, noResourceFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiExceptionResponse.res(badRequestException));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandler(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiExceptionResponse.res(e));
    }
}
