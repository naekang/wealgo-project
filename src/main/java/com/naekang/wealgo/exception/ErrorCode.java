package com.naekang.wealgo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ErrorCode {

    NOT_MATCH_PASSWORD(400, "NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다."),
    INVALID_REQUEST(400, "INVALID_REQUEST", "잘못된 요청입니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
    DUPLICATED_EMAIL(409, "DUPLICATED_EMAIL", "중복된 이메일입니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "내부 서버 오류");

    private final int status;
    private final String errorCode;
    private final String message;

}
