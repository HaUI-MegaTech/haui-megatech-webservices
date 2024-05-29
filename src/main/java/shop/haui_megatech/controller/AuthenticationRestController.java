package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.RefreshTokenRequestDTO;
import shop.haui_megatech.domain.dto.global.AuthData;
import shop.haui_megatech.domain.dto.global.BlankData;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.service.AuthenticationService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> register(
            @RequestBody AddUserRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.register(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.AUTHENTICATE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> authenticate(
            @RequestBody AuthenticationRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.authenticate(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.REFRESH)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> refresh(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.refresh(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.LOGOUT)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> logout(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.logout(request, servletRequest));
    }
}
