package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.product.CreateProductRequest;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.service.ProductService;

@RestApiV1
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "Get a Product by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(UrlConstant.Product.GET_PRODUCT_BY_ID)
    public ResponseEntity<?> getProductById(@PathVariable(name = "productId", required = true) Integer productId) {
        CommonResponseDTO<ProductDTO> response = productService.getProductById(productId);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Get Products with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS)
    public ResponseEntity<?> getProducts(PaginationRequestDTO request) {
        return ResponseUtil.ok(productService.getProducts(request));
    }


    @Operation(summary = "Create a new Product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PostMapping(UrlConstant.Product.CREATE_PRODUCT)
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseUtil.created(productService.createProduct(request ));
    }


    @Operation(summary = "Update a Product by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(UrlConstant.Product.UPDATE_PRODUCT)
    public ResponseEntity<?> updateProduct(
            @PathVariable(name = "productId", required = true) Integer productId,
            @RequestBody UpdateProductRequest request) {
        CommonResponseDTO<?> response = productService.updateProduct(productId, request);
        return response.result()
                ? ResponseUtil.ok(response)
                : ResponseUtil.notFound(response);
    }


    @Operation(summary = "Delete a Product by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "No Content"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT)
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "productId", required = true) Integer productId) {
        CommonResponseDTO<?> response = productService.deleteProductById(productId);
        return response.result()
                ? ResponseUtil.noContent(response)
                : ResponseUtil.notFound(response);
    }
}
