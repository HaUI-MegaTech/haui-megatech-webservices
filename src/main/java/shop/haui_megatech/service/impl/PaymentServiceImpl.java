package shop.haui_megatech.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.configuration.PaymentConfiguration;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.dto.global.BlankData;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.payment.CreatePaymentRequestDTO;
import shop.haui_megatech.domain.dto.payment.PaymentResponseDTO;
import shop.haui_megatech.domain.entity.CartItem;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.OrderDetail;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.entity.enums.OrderStatus;
import shop.haui_megatech.domain.entity.enums.PaymentMethod;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.*;
import shop.haui_megatech.service.PaymentService;
import shop.haui_megatech.utility.AuthenticationUtil;
import shop.haui_megatech.utility.PaymentUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentConfiguration paymentConfiguration;
    private final UserRepository       userRepository;
    private final CartItemRepository   cartItemRepository;
    private final OrderRepository      orderRepository;
    private final AddressRepository    addressRepository;
    private final ProductRepository    productRepository;

    @Override
    public PaymentResponseDTO createPayment(HttpServletRequest httpServletRequest, CreatePaymentRequestDTO request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
//        String ids = request.getParameter("ids");
        List<Integer> cartItemIds = Arrays.stream(request.ids().split(","))
                                          .map(String::trim)
                                          .map(Integer::valueOf)
                                          .toList();
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        Long amount = (long) cartItems.stream()
                                      .mapToDouble(item ->
                                              item.getQuantity() * item.getProduct().getCurrentPrice()
                                      )
                                      .sum() * 100L;

//        String bankCode = request.getParameter("bankCode");
        String bankCode = "NCB";

        String vnp_TxnRef = PaymentUtil.getRandomNumber(8);
        String vnp_IpAddr = PaymentUtil.getIpAddress(httpServletRequest);

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

//        String locate = request.getParameter("language");
        String locate = LocaleContextHolder.getLocale().toString();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", paymentConfiguration.getVnp_ReturnUrl() +
                                        "?ids=" + request.ids() +
                                        "&addressId=" + request.addressId() +
                                        "&userId=" + requestedUser.getId()
        );
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

        return PaymentResponseDTO
                .builder()
                .success(true)
                .url(paymentUrl)
                .build();
    }

    @Override
    public void resolvePayment(String ids, Integer userId, Integer addressId) {
        List<Integer> selectedCartItemIds = Arrays.stream(ids.split(","))
                                                  .map(String::trim)
                                                  .map(Integer::valueOf)
                                                  .toList();

        Order order = Order.builder()
                           .paymentMethod(PaymentMethod.THE_TIN_DUNG)
                           .payTime(new Date(Instant.now().toEpochMilli()))
                           .orderTime(new Date(Instant.now().toEpochMilli()))
                           .status(OrderStatus.PAID)
                           .tax(0.1f)
                           .address(addressRepository
                                   .findById(addressId)
                                   .orElseThrow(() ->
                                           new NotFoundException(ErrorMessage.Address.NOT_FOUND)
                                   )
                           )
                           .build();

        List<CartItem> selectedCartItems = cartItemRepository.findAllById(selectedCartItemIds);

        List<OrderDetail> orderDetails =
                selectedCartItems
                        .parallelStream()
                        .map(item -> OrderDetail.builder()
                                                .quantity(item.getQuantity())
                                                .price(item.getQuantity() * item.getProduct().getCurrentPrice())
                                                .order(null)
                                                .product(item.getProduct())
                                                .build()
                        )
                        .toList();
        order.setOrderDetails(orderDetails);
        order.setSubTotal((float) orderDetails.parallelStream().mapToDouble(OrderDetail::getPrice).sum());
        order.setTotal(order.getSubTotal() * (order.getTax() + 1));
        order.setUser(userRepository.findById(userId)
                                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND))
        );
        selectedCartItems.forEach(item -> {
            int curProdTotalSold = item.getProduct().getTotalSold();
            curProdTotalSold += item.getQuantity();
            int remaining = item.getProduct().getRemaining();
            remaining -= item.getQuantity();
            item.getProduct().setTotalSold(curProdTotalSold);
            item.getProduct().setRemaining(remaining);
            productRepository.save(item.getProduct());
        });

        orderDetails.forEach(orderDetail -> orderDetail.setOrder(order));
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        cartItemRepository.deleteAllByIds(selectedCartItemIds);
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> rejectPayment(String ids) {
        return null;
    }
}
