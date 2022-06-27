package com.lecuong.java09springboot.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCodeResponse errorCodeResponse;

    public BusinessException(ErrorCodeResponse errorCodeResponse) {
        this.errorCodeResponse = errorCodeResponse;
    }
}
