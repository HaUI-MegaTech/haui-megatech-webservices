package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequestDTO;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.service.base.*;

public interface UserService extends Addable<AddUserRequestDTO>,
                                     Gettable<UserDTO>,
                                     Updatable<UpdateUserInfoRequestDTO>,
                                     Importable,
                                     HardDeletable,
                                     SoftDeletable<UserDTO> {
    CommonResponseDTO<?> updatePassword(Integer userId, UpdateUserPasswordRequestDTO request);

    CommonResponseDTO<?> resetPasswordOne(Integer userId);

    CommonResponseDTO<?> resetPasswordList(ListIdsRequestDTO request);
}
