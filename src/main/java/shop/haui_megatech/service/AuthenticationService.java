package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO register(AddUserRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
