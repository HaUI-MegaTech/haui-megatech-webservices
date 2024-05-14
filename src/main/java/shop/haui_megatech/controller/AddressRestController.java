package shop.haui_megatech.controller;

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

    @PostMapping(Endpoint.Address.ADD_ONE)
    public ResponseEntity<?> addOne(
            @PathVariable Integer userId,
            @RequestBody AddressRequestDTO request
    ) {
        return ResponseUtil.created(addressService.addOne(userId, request));
    }

    @PutMapping(Endpoint.Address.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable Integer userId,
            @PathVariable Integer addressId,
            @RequestBody AddressRequestDTO request
    ) {
        return ResponseUtil.ok(addressService.updateOne(userId, addressId, request));
    }

    @DeleteMapping(Endpoint.Address.DELETE)
    public ResponseEntity<?> deleteOne(
            @PathVariable Integer userId,
            @PathVariable String addressIds
    ) {
        return ResponseUtil.ok(addressService.delete(userId, addressIds));
    }
}
