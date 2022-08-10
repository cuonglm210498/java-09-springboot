package com.lecuong.java09springboot.service;

import com.lecuong.java09springboot.modal.request.user.UserAuthRequest;
import com.lecuong.java09springboot.modal.request.user.UserCreateRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterWithListBlogRequest;
import com.lecuong.java09springboot.modal.response.ListDataResponse;
import com.lecuong.java09springboot.modal.response.user.UserResponse;
import com.lecuong.java09springboot.security.UserDetails;
import com.lecuong.java09springboot.security.jwt.model.JwtPayLoad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse auth(UserAuthRequest userAuthRequest);

    void save(UserCreateRequest userCreateRequest);

    void update(Long id, UserCreateRequest userCreateRequest);

    void delete(Long id);

    UserResponse findById(Long id);

    Page<UserResponse> getAll(Pageable pageable);

    Page<UserResponse> filter(UserFilterRequest userFilterRequest);

    boolean checkEmailExist(String email);

    boolean checkPhoneExits(String phone);

    boolean checkUserNameExist(String userName);

    UserDetails getUserDetail();

    JwtPayLoad getUser();

    UserResponse getInfo();

    List<UserResponse> getAllByCreatedBy();

    ListDataResponse<UserResponse> getAllByCondition(UserFilterRequest request);

    Page<UserResponse> filter(UserFilterWithListBlogRequest userFilterWithListBlogRequest);
}
