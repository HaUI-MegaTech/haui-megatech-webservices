package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@RequestBody AddUserRequestDTO request) {
        return ResponseUtil.ok(service.register(request));
    }

    @PostMapping(UrlConstant.Auth.AUTHENTICATE)
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDTO request) {
        return ResponseUtil.ok(service.authenticate(request));
    }
}
