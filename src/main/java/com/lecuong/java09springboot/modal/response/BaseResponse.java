package com.lecuong.java09springboot.modal.response;

import com.lecuong.java09springboot.exception.BusinessCodeResponse;
import com.lecuong.java09springboot.exception.BusinessException;
import com.lecuong.java09springboot.exception.ErrorCodeResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

@Getter
@Setter
public class BaseResponse<T> {

    private ErrorCodeResponse code;
    private T data;

    private BaseResponse(ErrorCodeResponse code) {
        this.code = code;
    }

    private BaseResponse(ErrorCodeResponse code, T data) {
        this.code = code;
        this.data = data;
    }

    public static BaseResponse<ErrorCodeResponse> ofSuccess() {
        return new BaseResponse<>(BusinessCodeResponse.SUCCESS);
    }

    public static <M> BaseResponse<M> ofSuccess(M data) {
        return new BaseResponse<>(BusinessCodeResponse.SUCCESS, data);
    }

    public static BaseResponse<ErrorCodeResponse> ofFail(BusinessException businessException) {
        return new BaseResponse<>(businessException.getErrorCodeResponse(), null);
    }

    public static BaseResponse<ErrorCodeResponse> ofFail(AccessDeniedException accessDeniedException) {
        return new BaseResponse<>(BusinessCodeResponse.FORBIDDEN, null);
    }
}
