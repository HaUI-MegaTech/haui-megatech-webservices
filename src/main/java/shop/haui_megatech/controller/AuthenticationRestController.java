package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.RefreshTokenRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<?> register(
            @RequestBody AddUserRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseUtil.ok(service.register(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.AUTHENTICATE)
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseUtil.ok(service.authenticate(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.REFRESH)
    public ResponseEntity<?> refresh(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseUtil.ok(service.refresh(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.LOGOUT)
    public ResponseEntity<?> logout(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseUtil.ok(service.logout(request, servletRequest));
    }
}
