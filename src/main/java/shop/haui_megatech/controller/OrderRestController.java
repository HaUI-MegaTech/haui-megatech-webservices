package shop.haui_megatech.controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.job.AutoMailer;
import shop.haui_megatech.repository.OrderRepository;
import shop.haui_megatech.service.OrderService;
import shop.haui_megatech.service.impl.OrderExportPdfServiceImpl;
import shop.haui_megatech.utility.QRCodeGeneratorUtil;
import shop.haui_megatech.utility.ResponseUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Orders Management REST API")
public class OrderRestController {
    private final OrderService OrderService;
    private final OrderRepository orderRepository;
    private final AutoMailer autoMailer;

    @Operation(summary = "Get an list Orders for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_LIST_BY_USER_ID)
    public ResponseEntity<?> getListOrdersByUserIdForUser(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.getListOrderForUser(requestDTO));
    }

    @Operation(summary = "Get an list Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_LIST_FOR_ADMIN)
    public ResponseEntity<?> getListOrdersByUserIdforAdmin(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.getListOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Get an Detail Orders for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_DETAIL_FOR_USER)
    public ResponseEntity<?> getDetailOrderForUser(
            @PathVariable(name = "orderId") Integer orderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForUser(orderId));
    }

    @Operation(summary = "Get an Detail Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_DETAIL_FOR_ADMIN)
    public ResponseEntity<?> getDetailOrderForAdmin(
            @PathVariable(name = "orderId") Integer orderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForAdmin(orderId));
    }

    @Operation(summary = "Add an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.V1.Order.ADD_ONE_FOR_USER)
    public ResponseEntity<?> addOrderForUser(
            @RequestBody AddOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.addOrderForUser(requestDTO));
    }

    @Operation(summary = "Add an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.V1.Order.ADD_ONE_FOR_ADMIN)
    public ResponseEntity<?> addOrderForAdmin(
            @RequestBody AddOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.addOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Update an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.V1.Order.UPDATED_ONE_FOR_UER)
    public ResponseEntity<?> updateOrderForUser(
            @RequestBody ModifyOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.updateOrderForUser(requestDTO));
    }

    @Operation(summary = "Update an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.V1.Order.UPDATED_ONE_FOR_ADMIN)
    public ResponseEntity<?> updateOrderForAdmin(
            @RequestBody ModifyOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.updateOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Delete an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.V1.Order.DELETE_ONE_ORDER)
    public ResponseEntity<?> deleteOrderForAdmin(
            @PathVariable(name = "orderId") int orderId
    ) {
        return ResponseUtil.ok(OrderService.deleteOrderForAdmin(orderId));
    }
    @Operation(summary = "Get an QR of Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_QR)
    public void getQr(
            @PathVariable(name = "orderId") Integer orderId,
            HttpServletResponse response
    ) throws IOException, WriterException, JSONException {
        Order order = orderRepository.findById(orderId).get();
        //QrOrderItemResponseDTO qrorder = orderMapper.qrOrderItemResponseDto(order);

        String contentQr = "http://192.168.0.102:8080/api/v1/orders/exportPdf/"+orderId;
        //String nameQr = "QrCodeExprotPdf-"+ orderId +".png";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formattedTime = dateFormat.format(new Date());

        String nameQr = "Id"+order.getId()+formattedTime+"-QrCodeExprotPdf";
        QRCodeGeneratorUtil.generateQRCode(contentQr, "QrForAPIExportPdf", nameQr, response.getOutputStream());
        response.getOutputStream().flush();
    }
    @Operation(summary = "Export PDF for Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.EXPORT_PDF)
    public void exportToPdf(@PathVariable(name = "orderId") Integer orderId
            ,HttpServletResponse response) throws DocumentException, IOException, JSONException, WriterException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Order order = orderRepository.findById(orderId).get();
        //QrOrderItemResponseDTO ordeqr = orderMapper.qrOrderItemResponseDto(order);
        OrderExportPdfServiceImpl exportPdfService = new OrderExportPdfServiceImpl();

        //exportPdfService.export(qrorder, order, response);
        exportPdfService.export(order, response);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedTime = dateFormat.format(new Date());

        String path = "src/main/resources/pdf/Order" + order.getId() + formattedTime + ".pdf";
        autoMailer.sendOrderPdf(order.getUser().getEmail(), path);
        System.out.println("Send success to email "+order.getUser().getEmail());
    }
    @Operation(summary = "Get statistic order by month")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_STATISTIC_BY_MONTH)
    public ResponseEntity<?> getStatisticByMonth(
//            @RequestParam("month") int month,
//            @RequestParam("year") int year
            ) {

        return ResponseUtil.ok(OrderService.statisticByMonth(3, 2022));
    }
    @Operation(summary = "Get statistic order by administrative region")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_STATISTIC_BY_ADMIN_REGION)
    public ResponseEntity<?> getStatisticAdminRegion(
//            @RequestParam(name="year") int year
    ) {
        return ResponseUtil.ok(OrderService.statisticByAdminRegion(2022));
    }
}
