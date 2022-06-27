package com.lecuong.java09springboot.mapper;

import com.lecuong.java09springboot.entity.Role;
import com.lecuong.java09springboot.entity.User;
import com.lecuong.java09springboot.modal.request.user.UserCreateRequest;
import com.lecuong.java09springboot.modal.response.user.UserResponse;
import com.lecuong.java09springboot.repository.RoleRepository;
import com.lecuong.java09springboot.utils.AlgorithmMd5;
import com.lecuong.java09springboot.utils.BeanUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * author CuongLM
 */
@Data
@Component
public class UserMapper implements Mapper {

    private final RoleRepository roleRepository;

    public User to(UserCreateRequest userCreateRequest) {
        User user = new User();
        BeanUtils.refine(userCreateRequest, user, BeanUtils::copyNonNull);
        user.setPassword(AlgorithmMd5.getMd5(userCreateRequest.getPassword()));

        Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(userCreateRequest.getIds()));
        user.setRoles(roles);

        return user;
    }

    public UserResponse to(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.refine(user, userResponse, BeanUtils::copyNonNull);
//        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
//        userResponse.setRoles(roles);
        return userResponse;
    }
}
