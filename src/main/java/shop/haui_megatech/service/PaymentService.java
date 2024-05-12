package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.PaymentResponseDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPayment(HttpServletRequest request);

    void resolvePayment(String ids);

    CommonResponseDTO<?> rejectPayment(String ids);
}
