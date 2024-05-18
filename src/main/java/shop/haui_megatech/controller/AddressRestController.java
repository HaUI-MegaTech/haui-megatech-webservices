package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.address.AddressRequestDTO;
import shop.haui_megatech.service.AddressService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Addresses Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class AddressRestController {
    private final AddressService addressService;


    @Operation(
            summary = "API Add an Address",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(Endpoint.V1.Address.ADD_ONE)
    public ResponseEntity<?> addOne(
            @PathVariable Integer userId,
            @RequestBody AddressRequestDTO request
    ) {
        return ResponseUtil.created(addressService.addOne(userId, request));
    }


    @Operation(
            summary = "API Update an Address",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping(Endpoint.V1.Address.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable Integer userId,
            @PathVariable Integer addressId,
            @RequestBody AddressRequestDTO request
    ) {
        return ResponseUtil.ok(addressService.updateOne(userId, addressId, request));
    }


    @Operation(
            summary = "API Delete an Address",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping(Endpoint.V1.Address.DELETE)
    public ResponseEntity<?> deleteOne(
            @PathVariable Integer userId,
            @PathVariable String addressIds
    ) {
        return ResponseUtil.ok(addressService.delete(userId, addressIds));
    }
}
