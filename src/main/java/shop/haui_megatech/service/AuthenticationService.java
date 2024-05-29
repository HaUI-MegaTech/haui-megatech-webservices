package shop.haui_megatech.service;

import jakarta.servlet.http.HttpServletRequest;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.RefreshTokenRequestDTO;
import shop.haui_megatech.domain.dto.global.AuthData;
import shop.haui_megatech.domain.dto.global.BlankData;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;

public interface AuthenticationService {
    GlobalResponseDTO<NoPaginatedMeta, AuthData> register(AddUserRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, AuthData> authenticate(AuthenticationRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, AuthData> refresh(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> logout(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);
}
