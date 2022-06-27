package com.lecuong.java09springboot.exception;

import com.lecuong.java09springboot.modal.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<ErrorCodeResponse>> handlerBusinessException(BusinessException e) {
        return new ResponseEntity<>(BaseResponse.ofFail(e), e.getErrorCodeResponse().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<ErrorCodeResponse>> handlerAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(BaseResponse.ofFail(e), HttpStatus.FORBIDDEN);
    }
}
