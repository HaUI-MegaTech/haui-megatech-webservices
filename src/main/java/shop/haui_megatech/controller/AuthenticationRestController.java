package shop.haui_megatech.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.RegisterRequestDTO;
import shop.haui_megatech.service.AuthenticationService;

@RestApiV1
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        return ResponseUtil.ok(service.register(request));
    }

    @PostMapping(UrlConstant.Auth.AUTHENTICATE)
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDTO request) {
        return ResponseUtil.ok(service.authenticate(request));
    }
}
