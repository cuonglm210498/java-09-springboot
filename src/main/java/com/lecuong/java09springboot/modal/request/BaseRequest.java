package com.lecuong.java09springboot.modal.request;

import lombok.Data;

/**
 * author CuongLM
 */
@Data
public class BaseRequest {

    private int pageIndex = 1;
    private int pageSize = 10;
}
