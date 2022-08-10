package com.lecuong.java09springboot.modal.request.user;

import com.lecuong.java09springboot.modal.request.BaseRequest;
import lombok.Data;

import java.util.List;

/**
 * @author CuongLM18
 * @created 10/08/2022 - 7:56 AM
 * @project java-09-springboot
 */
@Data
public class UserFilterWithListBlogRequest extends BaseRequest {

    private List<Long> ids;
}
