package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.domain.dto.user.UserDTO;

public interface UserService {
    CommonResponseDTO<UserDTO> getUserById(Integer userId);

    CommonResponseDTO<UserDTO> createUser(CreateUserRequestDTO request);

    CommonResponseDTO<?> updateUserInfo(Integer userId, UserDTO dto);

    CommonResponseDTO<?> updateUserPassword(Integer userId, UpdateUserPasswordRequest request);

    CommonResponseDTO<?> temporarilyDeleteUserById(Integer userId);

    CommonResponseDTO<?> permanentlyDeleteUserById(Integer userId);

    CommonResponseDTO<?> restoreDeletedUserById(Integer userId);

    PaginationResponseDTO<UserDTO> getActiveUsers(PaginationRequestDTO request);

    PaginationResponseDTO<UserDTO> getDeletedUsers(PaginationRequestDTO requestDTO);
}
