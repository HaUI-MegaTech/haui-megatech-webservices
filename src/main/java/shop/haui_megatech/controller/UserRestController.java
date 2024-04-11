package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.service.UserService;

@RestApiV1
@RequiredArgsConstructor
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
        CommonResponseDTO<UserDTO> response = userService.getUserById(userId);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Create a new User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
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
    @PostMapping(UrlConstant.User.UPDATE_USER_INFO)
    public ResponseEntity<?> updateUserInfo(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody UserDTO dto
    ) {
        CommonResponseDTO<?> response = userService.updateUserInfo(userId, dto);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
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
    @PostMapping(UrlConstant.User.UPDATE_USER_PASSWORD)
    public ResponseEntity<?> updateUserPassword(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody UpdateUserPasswordRequest request
    ) {
        CommonResponseDTO<?> response = userService.updateUserPassword(userId, request);

        return switch (response.message()) {
            case ErrorMessageConstant.User.NOT_FOUND -> ResponseUtil.notFound(response);
            case ErrorMessageConstant.User.WRONG_PASSWORD,
                 ErrorMessageConstant.User.MISMATCHED_PASSWORD -> ResponseUtil.badRequest(response);
            case SuccessMessageConstant.User.PASSWORD_UPDATED -> ResponseUtil.ok(response);
            default -> ResponseUtil.internalServerError(response);
        };
    }


    @Operation(summary = "Temporarily delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.User.TEMPORARILY_DELETE_USER)
    public ResponseEntity<?> temporarilyDeleteUserById(
            @PathVariable(value = "userId") Integer userId
    ) {
        CommonResponseDTO<?> response = userService.temporarilyDeleteUserById(userId);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Permanently delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.User.PERMANENTLY_DELETE_USER)
    public ResponseEntity<?> permanentlyDeleteUserById(
            @PathVariable(name = "userId") Integer userId
    ) {
        CommonResponseDTO<?> response = userService.permanentlyDeleteUserById(userId);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Restore a User from deleted Users by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.User.RESTORE_USER_BY_ID)
    public ResponseEntity<?> restoreDeletedUserById(
            @PathVariable(name = "userId") Integer userId
    ) {
        CommonResponseDTO<?> response = userService.restoreDeletedUserById(userId);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Get active Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(UrlConstant.User.GET_ACTIVE_USERS)
    public ResponseEntity<?> getActiveUsers(
            PaginationRequestDTO request
    ) {
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
    public ResponseEntity<?> getDeletedUsers(
            PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(userService.getDeletedUsers(request));
    }
}
