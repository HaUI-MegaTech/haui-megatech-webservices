package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.PaymentDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface PaymentService {
    PaymentDTO.Response createPayment(HttpServletRequest request);

    CommonResponseDTO<?> resolvePayment(String ids);

    CommonResponseDTO<?> rejectPayment(String ids);
}
