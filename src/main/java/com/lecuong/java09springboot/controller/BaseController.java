package com.lecuong.java09springboot.controller;

import com.lecuong.java09springboot.exception.ErrorCodeResponse;
import com.lecuong.java09springboot.modal.response.BaseResponse;
import org.springframework.http.ResponseEntity;

/**
 * author CuongLM
 */
public class BaseController {

    public ResponseEntity<BaseResponse<ErrorCodeResponse>> success() {
        return ResponseEntity.ok(BaseResponse.ofSuccess());
    }

    public <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(BaseResponse.ofSuccess(data));
    }
}
