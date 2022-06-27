package com.lecuong.java09springboot.validate;

import com.lecuong.java09springboot.exception.BusinessException;
import com.lecuong.java09springboot.exception.BusinessCodeResponse;
import com.lecuong.java09springboot.modal.request.user.UserAuthRequest;
import com.lecuong.java09springboot.modal.request.user.UserCreateRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterRequest;
import com.lecuong.java09springboot.service.UserService;
import com.lecuong.java09springboot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author CuongLM
 */
@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    public UserCreateRequest validateUserCreateRequest(UserCreateRequest userCreateRequest) {
        return Validator.data(userCreateRequest)
                .validate(UserCreateRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.USER_NAME_IS_EMPTY))
                .validate(UserCreateRequest::getUserName, userService::checkUserNameExist, () -> new BusinessException(BusinessCodeResponse.USER_NAME_AVAILABLE))
                .validate(UserCreateRequest::getPassword, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.PASSWORD_IS_EMPTY))
                .validate(UserCreateRequest::getPassword, StringUtils::verifyPassword, () -> new BusinessException(BusinessCodeResponse.PASSWORD_INVALIDATE))
                .validate(UserCreateRequest::getEmail, StringUtils::verifyEmail, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
                .validate(UserCreateRequest::getEmail, userService::checkEmailExist, () -> new BusinessException(BusinessCodeResponse.EMAIL_AVAILABLE))
                .validate(UserCreateRequest::getEmail, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
                .validate(UserCreateRequest::getPhone, StringUtils::verifyPhoneNumber, () -> new BusinessException(BusinessCodeResponse.PHONE_NUMBER_INVALIDATE))
                .validate(UserCreateRequest::getPhone, userService::checkPhoneExits, () -> new BusinessException(BusinessCodeResponse.PHONE_AVAILABLE))
                .getData();
    }

    public UserFilterRequest validateUserFilterRequest(UserFilterRequest userFilterRequest) {
        return Validator.data(userFilterRequest)
                .validate(UserFilterRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.USER_NAME_IS_EMPTY))
                .validate(UserFilterRequest::getUserName, userService::checkUserNameExist, () -> new BusinessException(BusinessCodeResponse.USER_NAME_AVAILABLE))
                .validate(UserFilterRequest::getEmail, StringUtils::verifyEmail, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
                .validate(UserFilterRequest::getEmail, userService::checkEmailExist, () -> new BusinessException(BusinessCodeResponse.EMAIL_AVAILABLE))
                .validate(UserFilterRequest::getEmail, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
                .validate(UserFilterRequest::getPhone, StringUtils::verifyPhoneNumber, () -> new BusinessException(BusinessCodeResponse.PHONE_NUMBER_INVALIDATE))
                .validate(UserFilterRequest::getPhone, userService::checkPhoneExits, () -> new BusinessException(BusinessCodeResponse.PHONE_AVAILABLE))
                .getData();
    }

    public UserAuthRequest validateUserAuthRequest(UserAuthRequest userAuthRequest) {
        return Validator.data(userAuthRequest)
                .validate(UserAuthRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.USER_NAME_IS_EMPTY))
                .validate(UserAuthRequest::getPassword, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.PASSWORD_IS_EMPTY))
                .validate(UserAuthRequest::getPassword, StringUtils::verifyPassword, () -> new BusinessException(BusinessCodeResponse.PASSWORD_INVALIDATE))
                .getData();
    }
}
