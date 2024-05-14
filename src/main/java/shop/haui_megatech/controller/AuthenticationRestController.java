package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping(Endpoint.Auth.REGISTER)
    public ResponseEntity<?> register(
            @RequestBody AddUserRequestDTO request
    ) {
        return ResponseUtil.ok(service.register(request));
    }

    @PostMapping(Endpoint.Auth.AUTHENTICATE)
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseUtil.ok(service.authenticate(request));
    }

    @PostMapping(Endpoint.Auth.REFRESH)
    public ResponseEntity<?> refresh(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseUtil.ok(service.refresh(request));
    }

}
