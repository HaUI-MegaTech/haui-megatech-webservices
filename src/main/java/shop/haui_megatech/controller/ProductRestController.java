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
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.common.ImportDataRequest;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequest;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Products Management REST API")
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "Get an active Product's details by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(UrlConstant.Product.GET_ONE)
    public ResponseEntity<?> getOne(
            @PathVariable(name = "productId") Integer productId
    ) {
        return ResponseUtil.ok(productService.getOne(productId));
    }


    @Operation(summary = "Get active Products with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(UrlConstant.Product.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getActiveList(request));
    }


    @Operation(
            summary = "Get hidden Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(UrlConstant.Product.GET_HIDDEN_LIST)
    public ResponseEntity<?> getHiddenList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getHiddenList(request));
    }


    @Operation(
            summary = "Get temporarily deleted Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(UrlConstant.Product.GET_DELETED_LIST)
    public ResponseEntity<?> getDeletedList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getDeletedList(request));
    }


    @Operation(
            summary = "Create a new Product",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created"),
            @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized")
    })
    @PostMapping(UrlConstant.Product.ADD_ONE)
    public ResponseEntity<?> addOne(
            @RequestBody AddProductRequest request
    ) {
        return ResponseUtil.created(productService.addOne(request));
    }


    @Operation(
            summary = "Add a list of Products using Excel file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "When has created successfully"),
            @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
            @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping(UrlConstant.Product.IMPORT_EXCEL)
    public ResponseEntity<?> importExcel(
            @ParameterObject ImportDataRequest request
    ) {
        return ResponseUtil.created(productService.importExcel(request));
    }


    @Operation(
            summary = "Add a list of Products using Csv file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "When has created successfully"),
            @ApiResponse(responseCode = "400", description = "When send empty username or password request"),
            @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping(UrlConstant.Product.IMPORT_CSV)
    public ResponseEntity<?> importCsv(
            @ParameterObject ImportDataRequest request
    ) {
        return ResponseUtil.created(productService.importCsv(request));
    }


    @Operation(
            summary = "Update a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PutMapping(UrlConstant.Product.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable(name = "productId") Integer productId,
            @RequestBody UpdateProductRequest request) {
        return ResponseUtil.ok(productService.updateOne(productId, request));
    }


    @Operation(
            summary = "Temporarily delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.Product.SOFT_DELETE_ONE)
    public ResponseEntity<?> softDeleteOne(
            @PathVariable(value = "userId") Integer userId
    ) {
        return ResponseUtil.ok(productService.softDeleteOne(userId));
    }


    @Operation(
            summary = "Temporarily delete Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.Product.SOFT_DELETE_LIST)
    public ResponseEntity<?> softDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.softDeleteList(request));
    }


    @Operation(
            summary = "Restore a Product from deleted Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.Product.RESTORE_ONE)
    public ResponseEntity<?> restoreOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(productService.restoreOne(userId));
    }


    @Operation(
            summary = "Restore Products from deleted Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(UrlConstant.Product.RESTORE_LIST)
    public ResponseEntity<?> restoreList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.restoreList(request));
    }


    @Operation(
            summary = "Hide a Product from active Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PatchMapping(UrlConstant.Product.HIDE_ONE)
    public ResponseEntity<?> hideOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(productService.hideOne(userId));
    }


    @Operation(
            summary = "Hide Products from active Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PatchMapping(UrlConstant.Product.HIDE_LIST)
    public ResponseEntity<?> hideList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.hideList(request));
    }


    @Operation(
            summary = "Unhide a Product from hidden Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PatchMapping(UrlConstant.Product.UNHIDE_ONE)
    public ResponseEntity<?> unhideOne(
            @PathVariable(name = "userId") Integer userId
    ) {
        return ResponseUtil.ok(productService.unhideOne(userId));
    }


    @Operation(
            summary = "Unhide Products from hidden Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PatchMapping(UrlConstant.Product.UNHIDE_LIST)
    public ResponseEntity<?> unhideList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.unhideList(request));
    }


    @Operation(
            summary = "Delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No Content"),
            @ApiResponse(responseCode = "403", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping(UrlConstant.Product.HARD_DELETE_ONE)
    public ResponseEntity<?> hardDeleteOne(
            @PathVariable(name = "productId") Integer productId
    ) {
        return ResponseUtil.noContent(productService.hardDeleteOne(productId));
    }


    @Operation(
            summary = "Permanently delete Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.Product.HARD_DELETE_LIST)
    public ResponseEntity<?> hardDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.hardDeleteList(request));
    }
}
