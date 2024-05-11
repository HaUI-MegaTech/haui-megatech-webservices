package shop.haui_megatech.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.service.PaymentService;
import shop.haui_megatech.utility.ResponseUtil;

import java.io.IOException;

@RestApiV1
@AllArgsConstructor
public class PaymentRestController {
    private final PaymentService paymentService;

    @GetMapping(Endpoint.Payment.CREATE)
    public ResponseEntity<?> createPayment(HttpServletRequest request) {
        return ResponseUtil.ok(paymentService.createPayment(request));
    }

    @GetMapping(Endpoint.Payment.CALLBACK)
    public void paymentCallbackHandler(HttpServletRequest request, HttpServletResponse response) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            try {
                response.sendRedirect("http://localhost:3000");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            return ResponseUtil.ok(
//                    PaymentDTO.Response
//                            .builder()
//                            .success(true)
//                            .message("call back thanh cong")
//                            .url("Mot duong link nao do...")
//                            .build()
//            );
//        } else {
//            return ResponseUtil.ok(
//                    PaymentDTO.Response
//                            .builder()
//                            .success(false)
//                            .message("call back that bai")
//                            .build()
//            );
//        }
        }
    }
}
