package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.payment.CreatePaymentRequestDTO;
import shop.haui_megatech.domain.dto.payment.PaymentResponseDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPayment(HttpServletRequest httpServletRequest, CreatePaymentRequestDTO request);

    void resolvePayment(String ids, Integer userId, Integer addressId);

    CommonResponseDTO<?> rejectPayment(String ids);
}
