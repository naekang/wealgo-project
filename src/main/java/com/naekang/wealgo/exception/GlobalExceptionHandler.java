package com.naekang.wealgo.exception;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("{} is occurred", e.getErrorCode());
        ErrorResponse response = new ErrorResponse(e.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception is occurred", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        log.error("MethodArgumentNotValidException is occurred: {}", Objects.requireNonNull(
                e.getFieldError())
            .getDefaultMessage());

        ErrorResponse response = ErrorResponse.builder()
            .status(400)
            .errorCode("INVALID_REQUEST")
            .message(e.getFieldError().getDefaultMessage())
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(
        HttpClientErrorException e
    ) {
        log.error("HttpClientErrorException is occured: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST_URL);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
