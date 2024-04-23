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
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.product.CreateProductRequest;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Products Management REST API")
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "Get a Product by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.Product.GET_ONE)
    public ResponseEntity<?> getOne(
            @PathVariable(name = "productId") Integer productId
    ) {
        return ResponseUtil.ok(productService.getOne(productId));
    }


    @Operation(summary = "Get Products with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(UrlConstant.Product.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(productService.getActiveList(request));
    }


    @Operation(summary = "Create a new Product", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PostMapping(UrlConstant.Product.ADD_ONE)
    public ResponseEntity<?> addOne(
            @RequestBody CreateProductRequest request
    ) {
        return ResponseUtil.created(productService.addOne(request));
    }


    @Operation(summary = "Update a Product by Id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(UrlConstant.Product.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable(name = "productId") Integer productId,
            @RequestBody UpdateProductRequest request) {
        return ResponseUtil.ok(productService.updateOne(productId, request));
    }


    @Operation(summary = "Delete a Product by Id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "No Content"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.Product.HARD_DELETE_ONE)
    public ResponseEntity<?> hardDeleteOne(
            @PathVariable(name = "productId") Integer productId
    ) {
        return ResponseUtil.noContent(productService.hardDeleteOne(productId));
    }
}
