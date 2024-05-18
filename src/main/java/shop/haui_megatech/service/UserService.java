package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.*;
import shop.haui_megatech.service.base.*;

public interface UserService {
    CommonResponseDTO<FullUserResponseDTO> getOneUser(Integer userId);

    CommonResponseDTO<?> addOneUser(AddUserRequestDTO request);

    CommonResponseDTO<?> importExcelUser(ImportDataRequestDTO request);

    CommonResponseDTO<?> importCsvUser(ImportDataRequestDTO request);

    CommonResponseDTO<?> updateInfoUser(
            Integer userId,
            UpdateUserInfoRequest request
    );

    CommonResponseDTO<?> updatePasswordUser(
            Integer userId,
            UpdateUserPasswordRequest request
    );

    CommonResponseDTO<?> softDeleteOneUser(Integer userId);

    CommonResponseDTO<?> softDeleteListUsers(ListIdsRequestDTO request);

    CommonResponseDTO<?> hardDeleteOneUser(Integer id);

    CommonResponseDTO<?> hardDeleteListUsers(ListIdsRequestDTO request);

    CommonResponseDTO<?> restoreOneUser(Integer userId);

    CommonResponseDTO<?> restoreListUsers(ListIdsRequestDTO request);

    CommonResponseDTO<?> resetPasswordOneUser(Integer userId);

    CommonResponseDTO<?> resetPasswordListUsers(ListIdsRequestDTO request);

    PaginationResponseDTO<BriefUserResponseDTO> getListActiveUsers(PaginationRequestDTO request);

    PaginationResponseDTO<BriefUserResponseDTO> getDeletedListUsers(PaginationRequestDTO request);
}
