package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.FilterProductRequestDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequestDTO;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Products Management REST API")
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "Get an active Product's details by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_DETAIL_ONE)
    public ResponseEntity<?> getOne(
            @PathVariable Integer productId
    ) {
        return ResponseUtil.ok(productService.getOne(productId));
    }


    @Operation(summary = "Filter active Products by brand ids, price range with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(Endpoint.V1.Product.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(
            @ParameterObject PaginationRequestDTO request,
            @RequestBody(required = false) FilterProductRequestDTO filter
    ) {
        return ResponseUtil.ok(productService.getList(request, filter));
    }


    @Operation(
            summary = "Get hidden Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_HIDDEN_LIST)
    public ResponseEntity<?> getHiddenList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getHiddenList(request));
    }


    @Operation(
            summary = "Get temporarily deleted Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_DELETED_LIST)
    public ResponseEntity<?> getDeletedList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getDeletedList(request));
    }


    @Operation(
            summary = "Create a new Product",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized")
            }
    )
    @PostMapping(Endpoint.V1.Product.ADD_ONE)
    public ResponseEntity<?> addOne(
            @RequestBody AddProductRequestDTO request
    ) {
        return ResponseUtil.created(productService.addOne(request));
    }


    @Operation(
            summary = "Add a list of Products using Excel file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PostMapping(Endpoint.V1.Product.IMPORT_EXCEL)
    public ResponseEntity<?> importExcel(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseUtil.created(productService.importExcel(request));
    }


    @Operation(
            summary = "Add a list of Products using Csv file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PostMapping(Endpoint.V1.Product.IMPORT_CSV)
    public ResponseEntity<?> importCsv(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseUtil.created(productService.importCsv(request));
    }


    @Operation(
            summary = "Update a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PutMapping(Endpoint.V1.Product.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable Integer productId,
            @RequestBody UpdateProductRequestDTO request) {
        return ResponseUtil.ok(productService.updateOne(productId, request));
    }


    @Operation(
            summary = "Update a list of Products from exported excel file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PutMapping(Endpoint.V1.Product.UPDATE_LIST_FROM_EXCEL)
    public ResponseEntity<?> updateListFromExcel(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseUtil.ok(productService.updateListFromExcel(request));
    }


    @Operation(
            summary = "Temporarily delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_ONE)
    public ResponseEntity<?> softDeleteOne(
            @PathVariable Integer productId
    ) {
        return ResponseUtil.ok(productService.softDeleteOne(productId));
    }


    @Operation(
            summary = "Temporarily delete Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_LIST)
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
            }
    )
    @PatchMapping(Endpoint.V1.Product.RESTORE_ONE)
    public ResponseEntity<?> restoreOne(
            @PathVariable Integer productId
    ) {
        return ResponseUtil.ok(productService.restoreOne(productId));
    }


    @Operation(
            summary = "Restore Products from deleted Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.RESTORE_LIST)
    public ResponseEntity<?> restoreList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.restoreList(request));
    }


    @Operation(
            summary = "Hide a Product from active Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.HIDE_ONE)
    public ResponseEntity<?> hideOne(
            @PathVariable Integer productId
    ) {
        return ResponseUtil.ok(productService.hideOne(productId));
    }


    @Operation(
            summary = "Hide Products from active Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.HIDE_LIST)
    public ResponseEntity<?> hideList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.hideList(request));
    }


    @Operation(
            summary = "Expose a Product to e-commercial marketplace by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.EXPOSE_ONE)
    public ResponseEntity<?> exposeOne(
            @PathVariable Integer productId
    ) {
        return ResponseUtil.ok(productService.exposeOne(productId));
    }


    @Operation(
            summary = "Expose Products to e-commercial marketplace by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.EXPOSE_LIST)
    public ResponseEntity<?> exposeList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.exposeList(request));
    }


    @Operation(
            summary = "Delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "No Content"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized"),
            }
    )
    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_ONE)
    public ResponseEntity<?> hardDeleteOne(
            @PathVariable Integer productId
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
            }
    )
    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_LIST)
    public ResponseEntity<?> hardDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseUtil.ok(productService.hardDeleteList(request));
    }
}
