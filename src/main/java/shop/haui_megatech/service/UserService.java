package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequest;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.*;

public interface UserService {
    CommonResponseDTO<UserDTO> getOne(Integer userId);

    CommonResponseDTO<?> addOne(AddUserRequestDTO request);

    CommonResponseDTO<?> importExcel(ImportDataRequest request);

    CommonResponseDTO<?> importCsv(ImportDataRequest request);

    CommonResponseDTO<?> updateInfo(Integer userId, UpdateUserInfoRequest request);

    CommonResponseDTO<?> updatePassword(Integer userId, UpdateUserPasswordRequest request);

    CommonResponseDTO<?> softDeleteOne(Integer userId);

    CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request);

    CommonResponseDTO<?> hardDeleteOne(Integer userId);

    CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request);

    CommonResponseDTO<?> restoreOne(Integer userId);

    CommonResponseDTO<?> restoreList(ListIdsRequestDTO request);

    CommonResponseDTO<?> resetPasswordOne(Integer userId);

    CommonResponseDTO<?> resetPasswordList(ListIdsRequestDTO request);

    PaginationResponseDTO<UserDTO> getActiveList(PaginationRequestDTO request);

    PaginationResponseDTO<UserDTO> getDeletedList(PaginationRequestDTO request);
}
