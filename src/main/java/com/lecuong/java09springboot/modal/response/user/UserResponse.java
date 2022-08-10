package com.lecuong.java09springboot.modal.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.lecuong.java09springboot.common.DateTimeCommon;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * author CuongLM
 */
@Data
public class UserResponse {

    private Long id;
    private String userName;
    private String email;
    private String phone;
    private String fullName;
    private String address;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = DateTimeCommon.DATE_TIME_FORMAT.DD_MM_YYYY, timezone = DateTimeCommon.DATE_TIME_FORMAT.TIME_ZONE)
    private LocalDate dob;

    private String facebook;
    private Boolean isActive;
    private transient List<String> roles;
}
