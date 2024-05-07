package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.AuthenticationDTO;
import shop.haui_megatech.domain.dto.UserDTO;

public interface AuthenticationService {
    AuthenticationDTO.Response register(UserDTO.AddRequest request);

    AuthenticationDTO.Response authenticate(AuthenticationDTO.Request request);

    AuthenticationDTO.Response refresh(AuthenticationDTO.Request request);
}
