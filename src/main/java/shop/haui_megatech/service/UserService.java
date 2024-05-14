package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.dto.user.BriefUserResponseDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequest;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.service.base.*;

public interface UserService extends Addable<AddUserRequestDTO>,
                                     Gettable<BriefUserResponseDTO>,
                                     Updatable<UpdateUserInfoRequest>,
                                     Importable,
                                     HardDeletable,
                                     SoftDeletable<BriefUserResponseDTO> {
    CommonResponseDTO<?> updatePassword(Integer userId, UpdateUserPasswordRequest request);

    CommonResponseDTO<?> resetPasswordOne(Integer userId);

    CommonResponseDTO<?> resetPasswordList(ListIdsRequestDTO request);
}
