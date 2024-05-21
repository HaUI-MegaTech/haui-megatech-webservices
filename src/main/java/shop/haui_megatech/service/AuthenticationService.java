package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.auth.RefreshTokenRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO register(AddUserRequestDTO request, HttpServletRequest servletRequest);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request, HttpServletRequest servletRequest);

    AuthenticationResponseDTO refresh(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);

    AuthenticationResponseDTO logout(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);
}
