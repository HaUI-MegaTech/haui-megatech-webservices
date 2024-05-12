package shop.haui_megatech.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.configuration.PaymentConfiguration;
import shop.haui_megatech.domain.dto.PaymentDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.repository.CartItemRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.CartItemService;
import shop.haui_megatech.service.InvoiceService;
import shop.haui_megatech.service.PaymentService;
import shop.haui_megatech.utility.PaymentUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentConfiguration paymentConfiguration;
    private final CartItemService      cartItemService;
    private final InvoiceService       orderService;
    private final UserRepository       userRepository;
    private final CartItemRepository   cartItemRepository;

    @Override
    public PaymentDTO.Response createPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");

        String vnp_TxnRef = PaymentUtil.getRandomNumber(8);
        String vnp_IpAddr = PaymentUtil.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", paymentConfiguration.getVnp_Version());
        vnp_Params.put("vnp_Command", paymentConfiguration.getVnp_Command());
        vnp_Params.put("vnp_TmnCode", paymentConfiguration.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", paymentConfiguration.getOrderType());

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", paymentConfiguration.getVnp_ReturnUrl() + "?ids=" + request.getParameter("ids"));
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentUtil.hmacSHA512(paymentConfiguration.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = paymentConfiguration.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentDTO.Response
                .builder()
                .success(true)
                .message("Ban dang thuc hien chuc nang thanh toan")
                .url(paymentUrl)
                .build();
    }

    @Override
    public CommonResponseDTO<?> resolvePayment(String ids) {
        List<Integer> cartItemIds = Arrays.stream(ids.split(","))
                                          .map(String::trim)
                                          .map(Integer::valueOf)
                                          .toList();

        cartItemRepository.deleteAllByIds(cartItemIds);

        return null;
    }

    @Override
    public CommonResponseDTO<?> rejectPayment(String ids) {
        return null;
    }
}
