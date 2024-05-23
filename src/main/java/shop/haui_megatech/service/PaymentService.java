package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.payment.CreatePaymentRequestDTO;
import shop.haui_megatech.domain.dto.payment.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPayment(HttpServletRequest httpServletRequest, CreatePaymentRequestDTO request);

    void resolvePayment(String ids, Integer userId, Integer addressId);

    GlobalResponseDTO<?> rejectPayment(String ids);
}
