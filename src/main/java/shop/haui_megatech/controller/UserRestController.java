package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequest;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.service.UserService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Users Management REST API")
public class UserRestController {
    private final UserService userService;

    @Operation(summary = "Get a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.User.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.getUserById(userId));
    }


    @Operation(summary = "Create a new User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(UrlConstant.User.CREATE_USER)
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequestDTO request
    ) {
        return ResponseUtil.created(userService.createUser(request));
    }


    @Operation(summary = "Update a User's info by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(UrlConstant.User.UPDATE_USER_INFO)
    public ResponseEntity<?> updateUserInfo(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody(required = false) UpdateUserInfoRequest request
    ) {
        return ResponseUtil.ok(userService.updateUserInfo(userId, request));
    }


    @Operation(summary = "Update a User's password by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PatchMapping(UrlConstant.User.UPDATE_USER_PASSWORD)
    public ResponseEntity<?> updateUserPassword(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody UpdateUserPasswordRequest request
    ) {
        return ResponseUtil.ok(userService.updateUserPassword(userId, request));
    }


    @Operation(summary = "Temporarily delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.TEMPORARILY_DELETE_USER)
    public ResponseEntity<?> temporarilyDeleteUserById(
            @PathVariable(value = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.temporarilyDeleteUserById(userId));
    }


    @Operation(summary = "Permanently delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.User.PERMANENTLY_DELETE_USER)
    public ResponseEntity<?> permanentlyDeleteUserById(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.permanentlyDeleteUserById(userId));
    }


    @Operation(summary = "Restore a User from deleted Users by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.RESTORE_USER_BY_ID)
    public ResponseEntity<?> restoreDeletedUserById(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.restoreDeletedUserById(userId));
    }


    @Operation(summary = "Get active Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(UrlConstant.User.GET_ACTIVE_USERS)
    public ResponseEntity<?> getActiveUsers(@ParameterObject PaginationRequestDTO request) {
        return ResponseUtil.ok(userService.getActiveUsers(request));
    }


    @Operation(summary = "Get deleted Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(UrlConstant.User.GET_DELETED_USERS)
    public ResponseEntity<?> getDeletedUsers(@ParameterObject PaginationRequestDTO request) {
        return ResponseUtil.ok(userService.getDeletedUsers(request));
    }
}
