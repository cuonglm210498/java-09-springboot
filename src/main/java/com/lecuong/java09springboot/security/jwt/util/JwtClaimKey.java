package com.lecuong.java09springboot.security.jwt.util;

public enum JwtClaimKey {

    ID("id"),
    USERNAME("userName"),
    FULLNAME("fullName"),
    ROLE("role");

    private String value;

    private JwtClaimKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
