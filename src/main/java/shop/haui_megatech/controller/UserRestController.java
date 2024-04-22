package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.common.ImportDataRequest;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequest;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.service.UserService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Users Management REST API")
@SecurityRequirement(name = "bearerAuth")
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
    @GetMapping(UrlConstant.User.GET_ONE)
    public ResponseEntity<?> getOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.getOne(userId));
    }


    @Operation(summary = "Add a new User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(UrlConstant.User.ADD_ONE)
    public ResponseEntity<?> addOne(
            @RequestBody AddUserRequestDTO request
    ) {
        return ResponseUtil.created(userService.addOne(request));
    }


    @Operation(summary = "Add a list of Users using Excel file")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(UrlConstant.User.IMPORT_EXCEL)
    public ResponseEntity<?> importExcel(
            @ParameterObject ImportDataRequest request
    ) {
        return ResponseUtil.created(userService.importExcel(request));
    }


    @Operation(summary = "Add a list of Users using Csv file")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(UrlConstant.User.IMPORT_CSV)
    public ResponseEntity<?> importCsv(
            @ParameterObject ImportDataRequest request
    ) {
        return ResponseUtil.created(userService.importCsv(request));
    }


    @Operation(summary = "Update a User's info by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(UrlConstant.User.UPDATE_INFO)
    public ResponseEntity<?> updateInfo(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody(required = false) UpdateUserInfoRequest request
    ) {
        return ResponseUtil.ok(userService.updateInfo(userId, request));
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
    @PatchMapping(UrlConstant.User.UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody UpdateUserPasswordRequest request
    ) {
        return ResponseUtil.ok(userService.updatePassword(userId, request));
    }


    @Operation(summary = "Temporarily delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.SOFT_DELETE_ONE)
    public ResponseEntity<?> softDeleteOne(
            @PathVariable(value = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.softDeleteOne(userId));
    }


    @Operation(summary = "Temporarily delete Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.SOFT_DELETE_LIST)
    public ResponseEntity<?> softDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(userService.softDeleteList(request));
    }


    @Operation(summary = "Permanently delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.User.HARD_DELETE_ONE)
    public ResponseEntity<?> hardDeleteOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.hardDeleteOne(userId));
    }


    @Operation(summary = "Permanently delete Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.User.HARD_DELETE_LIST)
    public ResponseEntity<?> hardDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(userService.hardDeleteList(request));
    }


    @Operation(summary = "Restore a User from deleted Users by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.RESTORE_ONE)
    public ResponseEntity<?> restoreOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.restoreOne(userId));
    }


    @Operation(summary = "Restore Users from deleted Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.RESTORE_LIST)
    public ResponseEntity<?> restoreList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(userService.restoreList(request));
    }


    @Operation(summary = "Reset User's password by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.RESET_PASSWORD_ONE)
    public ResponseEntity<?> resetPasswordOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(userService.resetPasswordOne(userId));
    }


    @Operation(summary = "Reset Users' password by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.User.RESET_PASSWORD_LIST)
    public ResponseEntity<?> resetPasswordList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(userService.resetPasswordList(request));
    }


    @Operation(summary = "Get active Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(UrlConstant.User.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(@ParameterObject PaginationRequestDTO request) {
        return ResponseUtil.ok(userService.getActiveList(request));
    }


    @Operation(summary = "Get deleted Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(UrlConstant.User.GET_DELETED_LIST)
    public ResponseEntity<?> getDeletedList(@ParameterObject PaginationRequestDTO request) {
        return ResponseUtil.ok(userService.getDeletedList(request));
    }
}
