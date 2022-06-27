package com.lecuong.java09springboot.controller.user;

import com.lecuong.java09springboot.controller.BaseController;
import com.lecuong.java09springboot.modal.request.user.UserAuthRequest;
import com.lecuong.java09springboot.modal.response.BaseResponse;
import com.lecuong.java09springboot.modal.response.user.UserResponse;
import com.lecuong.java09springboot.security.jwt.TokenProducer;
import com.lecuong.java09springboot.security.jwt.model.JwtPayLoad;
import com.lecuong.java09springboot.service.UserService;
import com.lecuong.java09springboot.validate.UserValidator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

/**
 * author CuongLM
 */
@Data
@RestController
@RequestMapping("/api/v1/users/login")
public class LoginController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserValidator userValidator;
    private final UserService userService;
    private final TokenProducer tokenProducer;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> login(@RequestBody UserAuthRequest userAuthRequest) {

        //Ghi log
        LOGGER.info(MessageFormat.format("request body: {0}" ,userAuthRequest.toString()));

        userValidator.validateUserAuthRequest(userAuthRequest);

        UserResponse userResponse = userService.auth(userAuthRequest);
        JwtPayLoad jwtPayload = createPayload(userResponse);
        String token = tokenProducer.token(jwtPayload);
        return success(token);
    }

    private JwtPayLoad createPayload(UserResponse userResponse){
        JwtPayLoad jwtPayload = new JwtPayLoad();
        jwtPayload.setUserName(userResponse.getUserName());
        jwtPayload.setId(userResponse.getId());
        jwtPayload.setFullName(userResponse.getFullName());
        //String role = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        String role = String.join(",", userResponse.getRoles());
        jwtPayload.setRole(role);

        return jwtPayload;
    }
}
