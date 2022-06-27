package com.lecuong.java09springboot.modal;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * author CuongLM
 */
@Data
@RequestScope
@Component
public class AccountInfo {

    private String iss;
    private String sub;
    private String aud;
    private String exp;
    private String nbf;
    private String role;
    private String fullName;
    private Long id;
    private String userName;

    //private List<String> role;
}
