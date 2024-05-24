package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.user.*;
import shop.haui_megatech.service.UserService;

import java.util.List;

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
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.User.GET_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> getOne(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOneUser(userId));
    }


    @Operation(summary = "Add a new User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    //@ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    //@ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(Endpoint.V1.User.ADD_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BriefUserResponseDTO>> addOneUser(
            @RequestBody @Valid AddUserRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addOneUser(request));
    }


    @Operation(summary = "Add a list of Users using Excel file")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    //@ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    //@ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(Endpoint.V1.User.IMPORT_EXCEL)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importExcelUser(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.importExcelUser(request));
    }


    @Operation(summary = "Add a list of Users using Csv file")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    //@ApiResponse(responseCode = "400", description = "When send empty username or password request"),
                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
                    //@ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @PostMapping(Endpoint.V1.User.IMPORT_CSV)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importCsvUser(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.importCsvUser(request));
    }


    @Operation(summary = "Update a User's info by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.V1.User.UPDATE_INFO)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> updateInfoUser(
            @PathVariable Integer userId,
            @ModelAttribute UpdateUserInfoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateInfoUser(userId, request));
    }


    @Operation(summary = "Update a User's password by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "400", description = "Bad Request"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found"),
                    //@ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PatchMapping(Endpoint.V1.User.UPDATE_PASSWORD)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> updatePasswordUser(
            @PathVariable Integer userId,
            @RequestBody UpdateUserPasswordRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePasswordUser(userId, request));
    }


    @Operation(summary = "Temporarily delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.SOFT_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> softDeleteOneUser(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.softDeleteOneUser(userId));
    }


    @Operation(summary = "Temporarily delete Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.SOFT_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> softDeleteListUsers(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.softDeleteListUsers(request));
    }


    @Operation(summary = "Permanently delete a User by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.V1.User.HARD_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hardDeleteOneUser(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.hardDeleteOneUser(userId));
    }


    @Operation(summary = "Permanently delete Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.V1.User.HARD_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hardDeleteListUsers(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.hardDeleteListUsers(request));
    }


    @Operation(summary = "Restore a User from deleted Users by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.RESTORE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> restoreOneUser(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.restoreOneUser(userId));
    }


    @Operation(summary = "Restore Users from deleted Users by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.RESTORE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> restoreListUsers(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.restoreListUsers(request));
    }


    @Operation(summary = "Reset User's password by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.RESET_PASSWORD_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> resetPasswordOneUser(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPasswordOneUser(userId));
    }


    @Operation(summary = "Reset Users' password by a list of Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(Endpoint.V1.User.RESET_PASSWORD_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> resetPasswordListUsers(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPasswordListUsers(request));
    }


    @Operation(summary = "Get active Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(Endpoint.V1.User.GET_ACTIVE_LIST)
    public ResponseEntity<
            GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>>
            > getActiveList(@ParameterObject PaginationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getListActiveUsers(request));
    }


    @Operation(summary = "Get deleted Users with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(Endpoint.V1.User.GET_DELETED_LIST)
    public ResponseEntity<
            GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>>
            > getDeletedList(@ParameterObject PaginationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getDeletedListUsers(request));
    }
}
