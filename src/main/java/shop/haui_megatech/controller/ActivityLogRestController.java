package shop.haui_megatech.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.service.ActivityLogService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
public class ActivityLogRestController {
    private final ActivityLogService activityLogService;

    @GetMapping(Endpoint.V1.ActivityLog.GET_LIST)
    public ResponseEntity<?> getList(PaginationRequestDTO request) {
        return ResponseUtil.ok(activityLogService.getList(request));
    }
}
