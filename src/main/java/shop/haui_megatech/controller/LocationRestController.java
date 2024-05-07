package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.service.LocationService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Location")
@SecurityRequirement(name = "bearerAuth")
public class LocationRestController {
    private final LocationService locationService;

    @GetMapping(UrlConstant.Location.GET_ALL_PROVINCES)
    public ResponseEntity<?> getAllProvinces() {
        return ResponseEntity.ok(locationService.getProvinces());
    }

    @GetMapping(UrlConstant.Location.GET_ALL_DISTRICTS)
    public ResponseEntity<?> getAllDistrictsByProvince(
            @PathVariable("provinceCode") String provinceCode
    ) {
        return ResponseEntity.ok(locationService.getDistrictsByProvince(provinceCode));
    }

    @GetMapping(UrlConstant.Location.GET_ALL_WARDS)
    public ResponseEntity<?> getAllWardsByDistrict(
            @PathVariable("provinceCode") String provinceCode,
            @PathVariable("districtCode") String districtCode
    ) {
        return ResponseEntity.ok(locationService.getWardsByDistrict(districtCode));
    }
}