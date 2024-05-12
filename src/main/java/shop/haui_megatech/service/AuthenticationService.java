package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO register(AddUserRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    AuthenticationResponseDTO refresh(AuthenticationRequestDTO request);
}
