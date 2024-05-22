package shop.haui_megatech.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.payment.CreatePaymentRequestDTO;
import shop.haui_megatech.service.PaymentService;
import shop.haui_megatech.utility.ResponseUtil;

import java.io.IOException;

@RestApiV1
@AllArgsConstructor
public class PaymentRestController {
    private final PaymentService paymentService;

    @PostMapping(Endpoint.V1.Payment.CREATE)
    public ResponseEntity<?> createPayment(
            HttpServletRequest httpServletRequest,
            @ParameterObject CreatePaymentRequestDTO request
    ) {
        return ResponseUtil.ok(paymentService.createPayment(httpServletRequest, request));
    }

    @GetMapping(Endpoint.V1.Payment.CALLBACK)
    public void paymentCallbackHandler(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            paymentService.resolvePayment(
                    request.getParameter("ids"),
                    Integer.valueOf(request.getParameter("userId")),
                    Integer.valueOf(request.getParameter("addressId"))
            );
            response.sendRedirect("http://localhost:3000/payment-success");
        } else {
            response.sendRedirect("http://localhost:3000/payment-error");
        }
    }
}
