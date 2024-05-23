package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.service.BrandService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Brands Management REST API")
public class BrandRestController {
    private final BrandService brandService;

    @Operation(summary = "Get an active Brand by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Brand.GET_ONE)
    public ResponseEntity<?> getOne(
            @PathVariable Integer brandId
    ) {
        return ResponseUtil.ok(brandService.getOne(brandId));
    }


    @Operation(summary = "Get active Brands with pagination")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(Endpoint.V1.Brand.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(brandService.getList(request));
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_REVENUE)
    public ResponseEntity<?> getTotalRevenue() {
        return ResponseUtil.ok(brandService.getTotalRevenue());
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_SOLD)
    public ResponseEntity<?> getTotalSold() {
        return ResponseUtil.ok(brandService.getTotalSold());
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_VIEW)
    public ResponseEntity<?> getTotalView() {
        return ResponseUtil.ok(brandService.getTotalView());
    }
}
