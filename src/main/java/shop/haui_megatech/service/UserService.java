package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.user.*;

import java.util.List;

public interface UserService {
    GlobalResponseDTO<FullUserResponseDTO> getOneUser(Integer userId);

    GlobalResponseDTO<?> addOneUser(AddUserRequestDTO request);

    GlobalResponseDTO<?> importExcelUser(ImportDataRequestDTO request);

    GlobalResponseDTO<?> importCsvUser(ImportDataRequestDTO request);

    GlobalResponseDTO<?> updateInfoUser(
            Integer userId,
            UpdateUserInfoRequest request
    );

    GlobalResponseDTO<?> updatePasswordUser(
            Integer userId,
            UpdateUserPasswordRequest request
    );

    GlobalResponseDTO<?> softDeleteOneUser(Integer userId);

    GlobalResponseDTO<?> softDeleteListUsers(ListIdsRequestDTO request);

    GlobalResponseDTO<?> hardDeleteOneUser(Integer id);

    GlobalResponseDTO<?> hardDeleteListUsers(ListIdsRequestDTO request);

    GlobalResponseDTO<?> restoreOneUser(Integer userId);

    GlobalResponseDTO<?> restoreListUsers(ListIdsRequestDTO request);

    GlobalResponseDTO<?> resetPasswordOneUser(Integer userId);

    GlobalResponseDTO<?> resetPasswordListUsers(ListIdsRequestDTO request);

    GlobalResponseDTO<List<BriefUserResponseDTO>> getListActiveUsers(PaginationRequestDTO request);

    GlobalResponseDTO<List<BriefUserResponseDTO>> getDeletedListUsers(PaginationRequestDTO request);
}
