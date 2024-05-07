package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.UserDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.service.base.*;

public interface UserService extends Addable<UserDTO.AddRequest>,
                                     Gettable<UserDTO.Response>,
                                     Updatable<UserDTO.UpdateInfoRequest>,
                                     Importable,
                                     HardDeletable,
                                     SoftDeletable<UserDTO.Response> {
    CommonResponseDTO<?> updatePassword(Integer userId, UserDTO.UpdatePasswordRequest request);

    CommonResponseDTO<?> resetPasswordOne(Integer userId);

    CommonResponseDTO<?> resetPasswordList(ListIdsRequestDTO request);
}
