package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.order.AddOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.AddOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.order.ModifyOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.ModifyOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.service.InvoiceService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "invoices Management REST API")
public class InvoiceRestController {
    private final InvoiceService invoiceService;

    @Operation(summary = "Get an list invoices for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Invoice.GET_LIST_BY_USER_ID)
    public ResponseEntity<?> getListInvoicesByUserIdForUser(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.getListInvoiceForUser(requestDTO));
    }

    @Operation(summary = "Get an list invoices for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Invoice.GET_LIST_FOR_ADMIN)
    public ResponseEntity<?> getListinvoicesByUserIdforAdmin(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.getListInvoiceForAdmin(requestDTO));
    }

    @Operation(summary = "Get an Detail invoices for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Invoice.GET_DETAIL_FOR_USER)
    public ResponseEntity<?> getDetailInvoiceForUser(
            @PathVariable(name = "invoiceId") Integer invoiceId
    ) {
        return ResponseUtil.ok(invoiceService.getInvoiceDetailForUser(invoiceId));
    }

    @Operation(summary = "Get an Detail invoices for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Invoice.GET_DETAIL_FOR_ADMIN)
    public ResponseEntity<?> getDetailInvoiceForAdmin(
            @PathVariable(name = "invoiceId") Integer invoiceId
    ) {
        return ResponseUtil.ok(invoiceService.getInvoiceDetailForAdmin(invoiceId));
    }

    @Operation(summary = "Add an invoice for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.Invoice.ADD_ONE_FOR_USER)
    public ResponseEntity<?> getDetailInvoiceForUser(
            @RequestBody AddOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.addInvoiceForUser(requestDTO));
    }

    @Operation(summary = "Add an invoice for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.Invoice.ADD_ONE_FOR_ADMIN)
    public ResponseEntity<?> getDetailinvoiceForAdmin(
            @RequestBody AddOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.addInvoiceForAdmin(requestDTO));
    }

    @Operation(summary = "Update an invoice for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.Invoice.UPDATED_ONE_FOR_UER)
    public ResponseEntity<?> updateinvoiceForUser(
            @RequestBody ModifyOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.updateInvoiceForUser(requestDTO));
    }

    @Operation(summary = "Update an invoice for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.Invoice.UPDATED_ONE_FOR_ADMIN)
    public ResponseEntity<?> updateinvoiceForAdmin(
            @RequestBody ModifyOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(invoiceService.updateInvoiceForAdmin(requestDTO));
    }

    @Operation(summary = "Delete an invoice for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.Invoice.DELETE_ONE_ORDER)
    public ResponseEntity<?> deleteinvoiceForAdmin(
            @PathVariable(name = "invoiceId") int invoiceId
    ) {
        return ResponseUtil.ok(invoiceService.deleteInvoiceForAdmin(invoiceId));
    }
}
