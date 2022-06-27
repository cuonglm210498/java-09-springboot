package com.lecuong.java09springboot.controller.user;

import com.lecuong.java09springboot.controller.BaseController;
import com.lecuong.java09springboot.modal.AccountInfo;
import com.lecuong.java09springboot.modal.request.user.UserCreateRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterRequest;
import com.lecuong.java09springboot.modal.response.BaseResponse;
import com.lecuong.java09springboot.modal.response.ListDataResponse;
import com.lecuong.java09springboot.modal.response.user.UserResponse;
import com.lecuong.java09springboot.service.UserService;
import com.lecuong.java09springboot.validate.UserValidator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.text.MessageFormat;
import java.util.List;

/**
 * author CuongLM
 */
@Data
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final AccountInfo accountInfo;
    private final UserValidator userValidator;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody UserCreateRequest userCreateRequest) {

        //Ghi log
        LOGGER.info(MessageFormat.format("request body: {0}" ,userCreateRequest.toString()));

        userValidator.validateUserCreateRequest(userCreateRequest);
        userService.save(userCreateRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return success(null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable Long id,
                                                     @RequestBody UserCreateRequest userCreateRequest) {
        userValidator.validateUserCreateRequest(userCreateRequest);
        userService.update(id, userCreateRequest);
        return success(null);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return success(userResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getAll(@RequestParam int pageIndex,
                                                                   @RequestParam int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<UserResponse> userResponses = userService.getAll(pageRequest);
        return success(userResponses);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> filter(@ModelAttribute UserFilterRequest userFilterRequest) {
        userValidator.validateUserFilterRequest(userFilterRequest);
        Page<UserResponse> userResponses = userService.filter(userFilterRequest);
        return success(userResponses);
    }

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<UserResponse>> getInfo() {
        UserResponse response = userService.getInfo();
        return success(response);
    }

    @GetMapping("/createdBy")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<List<UserResponse>>> getByCreatedBy() {
        List<UserResponse> userDetailResponses = userService.getAllByCreatedBy();
        return success(userDetailResponses);
    }

    @GetMapping("/getAllByCondition")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<ListDataResponse<UserResponse>>> getAllByCondition(@ModelAttribute UserFilterRequest request) {
        userValidator.validateUserFilterRequest(request);
        ListDataResponse<UserResponse> userDetailResponses = userService.getAllByCondition(request);
        return success(userDetailResponses);
    }
}
