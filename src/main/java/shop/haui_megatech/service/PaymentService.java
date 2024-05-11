package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO.Response createPayment(HttpServletRequest request);
}
