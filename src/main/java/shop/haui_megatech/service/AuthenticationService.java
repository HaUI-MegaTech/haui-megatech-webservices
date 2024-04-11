package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO register(CreateUserRequestDTO request);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
