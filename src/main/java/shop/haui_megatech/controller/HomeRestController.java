package shop.haui_megatech.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.service.HomeService;
import shop.haui_megatech.service.LoginStatisticService;
import shop.haui_megatech.utility.ResponseUtil;

@RestController
@RequiredArgsConstructor
public class HomeRestController {
    private final LoginStatisticService loginStatisticService;
    private final HomeService           homeService;

    @GetMapping(Endpoint.V1.LoginStatistic.GET_LIST_BY_DAY)
    public ResponseEntity<?> getLoginStatisticsByDay() {
        return ResponseUtil.ok(loginStatisticService.getListByDay());
    }

    @GetMapping(Endpoint.V1.Brand.GET_STATISTIC)
    public ResponseEntity<?> getBrandStatistics() {
        return ResponseUtil.ok(homeService.getProductCountByBrand());
    }
}
