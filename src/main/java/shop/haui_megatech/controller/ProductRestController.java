package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import shop.haui_megatech.domain.dto.product.*;
import shop.haui_megatech.service.ProductService;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Products Management REST API")
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "Get an active Product's details by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_DETAIL_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullProductResponseDTO>> getOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getOne(productId));
    }


    @Operation(summary = "Filter active Products by brand ids, price range with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(Endpoint.V1.Product.GET_ACTIVE_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getActiveList(
            @ParameterObject FilterProductPaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getList(
                        PaginationRequestDTO
                                .builder()
                                .index(request.index())
                                .direction(request.direction())
                                .limit(request.limit())
                                .fields(request.fields())
                                .build(),
                        FilterProductRequestDTO
                                .builder()
                                .brandIds(request.brandIds())
                                .minPrice(request.minPrice())
                                .maxPrice(request.maxPrice())
                                .build()
                ));
    }


    @Operation(
            summary = "Get hidden Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_HIDDEN_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getHiddenList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getHiddenList(request));
    }


    @Operation(
            summary = "Get temporarily deleted Products with pagination",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @GetMapping(Endpoint.V1.Product.GET_DELETED_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getDeletedList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getDeletedList(request));
    }


    @Operation(
            summary = "Create a new Product",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Created"),
//                    //@ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized")
            }
    )
    @PostMapping(Endpoint.V1.Product.ADD_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BriefProductResponseDTO>> addOne(
            @RequestBody AddProductRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addOne(request));
    }


    @Operation(
            summary = "Add a list of Products using Excel file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
//                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PostMapping(Endpoint.V1.Product.IMPORT_EXCEL)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importExcel(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.importExcel(request));
    }


    @Operation(
            summary = "Add a list of Products using Csv file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "When has created successfully"),
//                    //@ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PostMapping(Endpoint.V1.Product.IMPORT_CSV)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importCsv(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.importCsv(request));
    }


    @Operation(
            summary = "Update a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PutMapping(Endpoint.V1.Product.UPDATE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> updateOne(
            @PathVariable Integer productId,
            @RequestBody UpdateProductRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateOne(productId, request));
    }


    @Operation(
            summary = "Update a list of Products from exported excel file",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Passing unmatched datatype or unauthorized"),
            }
    )
    @PutMapping(Endpoint.V1.Product.UPDATE_LIST_FROM_EXCEL)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> updateListFromExcel(
            @ParameterObject ImportDataRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateListFromExcel(request));
    }


    @Operation(
            summary = "Temporarily delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> softDeleteOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.softDeleteOne(productId));
    }


    @Operation(
            summary = "Temporarily delete Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> softDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.softDeleteList(request));
    }


    @Operation(
            summary = "Restore a Product from deleted Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.RESTORE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> restoreOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.restoreOne(productId));
    }


    @Operation(
            summary = "Restore Products from deleted Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.RESTORE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> restoreList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.restoreList(request));
    }


    @Operation(
            summary = "Hide a Product from active Products by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.HIDE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hideOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hideOne(productId));
    }


    @Operation(
            summary = "Hide Products from active Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.HIDE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hideList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hideList(request));
    }


    @Operation(
            summary = "Expose a Product to e-commercial marketplace by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.EXPOSE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> exposeOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.exposeOne(productId));
    }


    @Operation(
            summary = "Expose Products to e-commercial marketplace by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PatchMapping(Endpoint.V1.Product.EXPOSE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> exposeList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.exposeList(request));
    }


    @Operation(
            summary = "Delete a Product by Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "No Content"),
                    //@ApiResponse(responseCode = "403", description = "Unauthorized"),
            }
    )
    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hardDeleteOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.hardDeleteOne(productId));
    }


    @Operation(
            summary = "Permanently delete Products by a list of Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hardDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hardDeleteList(request));
    }
}
