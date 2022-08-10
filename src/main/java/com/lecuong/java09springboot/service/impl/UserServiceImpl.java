package com.lecuong.java09springboot.service.impl;

import com.lecuong.java09springboot.entity.Role;
import com.lecuong.java09springboot.entity.User;
import com.lecuong.java09springboot.exception.BusinessException;
import com.lecuong.java09springboot.exception.BusinessCodeResponse;
import com.lecuong.java09springboot.mapper.UserMapper;
import com.lecuong.java09springboot.modal.request.user.UserAuthRequest;
import com.lecuong.java09springboot.modal.request.user.UserCreateRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterWithListBlogRequest;
import com.lecuong.java09springboot.modal.response.ListDataResponse;
import com.lecuong.java09springboot.modal.response.user.UserResponse;
import com.lecuong.java09springboot.repository.RoleRepository;
import com.lecuong.java09springboot.repository.UserRepository;
import com.lecuong.java09springboot.repository.specification.UserSpecification;
import com.lecuong.java09springboot.security.UserDetails;
import com.lecuong.java09springboot.security.jwt.model.JwtPayLoad;
import com.lecuong.java09springboot.service.UserService;
import com.lecuong.java09springboot.utils.AlgorithmMd5;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author CuongLM
 */
@Data
@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponse auth(UserAuthRequest userAuthRequest) {
        String passwordHash = AlgorithmMd5.getMd5(userAuthRequest.getPassword());
        Optional<User> user = userRepository.findUserByUserNameAndPassword(userAuthRequest.getUserName(), passwordHash);
        user.orElseThrow(() -> new BusinessException(BusinessCodeResponse.USER_NOT_FOUND));

        UserResponse userResponse = userMapper.to(user.get());
        List<String> roleNames = user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userResponse.setRoles(roleNames);
        return userResponse;
    }

    @Override
    public void save(UserCreateRequest userCreateRequest) {
        User user = userMapper.to(userCreateRequest);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserCreateRequest userCreateRequest) {
        userRepository.findById(id)
                .map(user -> {
                    user.setUserName(userCreateRequest.getUserName());
                    user.setPassword(AlgorithmMd5.getMd5(userCreateRequest.getPassword()));
                    user.setEmail(userCreateRequest.getEmail());
                    user.setPhone(userCreateRequest.getPhone());
                    user.setAddress(userCreateRequest.getAddress());
                    user.setDob(userCreateRequest.getDob());
                    user.setFacebook(userCreateRequest.getFacebook());
                    user.setDob(userCreateRequest.getDob());

                    Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(userCreateRequest.getIds()));
                    user.setRoles(roles);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new BusinessException(BusinessCodeResponse.USER_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new BusinessException(BusinessCodeResponse.USER_NOT_FOUND));

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new BusinessException(BusinessCodeResponse.USER_NOT_FOUND));

        return userMapper.to(user.get());
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable.previousOrFirst());
        return users.map(userMapper::to);
    }

    @Override
    public Page<UserResponse> filter(UserFilterRequest userFilterRequest) {
        PageRequest pageRequest = PageRequest.of(userFilterRequest.getPageIndex(), userFilterRequest.getPageSize());
        Page<User> users = userRepository.findAll(UserSpecification.filter(userFilterRequest), pageRequest.previousOrFirst());
        return users.map(userMapper::to);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userRepository.countAllByEmail(email) > 0;
    }

    @Override
    public boolean checkPhoneExits(String phone) {
        return userRepository.countAllByPhone(phone) > 0;
    }

    @Override
    public boolean checkUserNameExist(String userName) {
        return userRepository.countAllByUserName(userName) > 0;
    }

    @Override
    public UserDetails getUserDetail() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    @Override
    public JwtPayLoad getUser() {
        return getUserDetail().getUser();
    }

    @Override
    public UserResponse getInfo() {
        JwtPayLoad jwtPayload = getUser();
        User user = userRepository.getById(jwtPayload.getId());
        return userMapper.to(user);
    }

    @Override
    public List<UserResponse> getAllByCreatedBy() {
        String userName = getUser().getUserName();
        List<User> users = userRepository.getByCreatedBy(userName);
        return userMapper.toList(users, userMapper::to);
    }

    @Override
    public ListDataResponse<UserResponse> getAllByCondition(UserFilterRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageIndex(), request.getPageSize());
        Page<User> users = userRepository
                .findAll(UserSpecification.filter(request), pageRequest.previousOrFirst());

        long totalPage = users.getTotalPages();
        List<UserResponse> userDetailResponses = userMapper.toList(users.getContent(), userMapper::to);


        return ListDataResponse
                .of(totalPage, userDetailResponses);
    }

    @Override
    public Page<UserResponse> filter(UserFilterWithListBlogRequest userFilterWithListBlogRequest) {
        PageRequest pageRequest = PageRequest.of(userFilterWithListBlogRequest.getPageIndex(), userFilterWithListBlogRequest.getPageSize());
        Page<User> users = userRepository.findAll(UserSpecification.filter(userFilterWithListBlogRequest), pageRequest.previousOrFirst());
        return users.map(userMapper::to);
    }
}
