package com.lecuong.java09springboot.security.jwt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtPayLoad {

    private long id;
    private String userName;
    private String fullName;
    private String role;
    //private List<String> role;
}
