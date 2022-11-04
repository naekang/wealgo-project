package com.naekang.wealgo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String errorCode;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}
