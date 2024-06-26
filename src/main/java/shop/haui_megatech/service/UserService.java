package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.user.*;

import java.util.List;

public interface UserService {
    // Get
    GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> getOneUser(Integer userId);

    GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getListActiveUsers(PaginationRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getDeletedListUsers(PaginationRequestDTO request);


    // Add
    GlobalResponseDTO<NoPaginatedMeta, BriefUserResponseDTO> addOneUser(AddUserRequestDTO request);


    // Import
    GlobalResponseDTO<NoPaginatedMeta, BlankData> importExcelUser(ImportDataRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> importCsvUser(ImportDataRequestDTO request);


    // Update info
    GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> updateInfoUser(
            Integer userId,
            UpdateUserInfoRequest request
    );


    // Update password
    GlobalResponseDTO<NoPaginatedMeta, BlankData> updatePasswordUser(
            Integer userId,
            UpdateUserPasswordRequest request
    );


    // Soft delete
    GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteListUsers(String userIds);


    // Hard delete
    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteOneUser(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteListUsers(String userIds);


    // Restore
    GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreListUsers(String userIds);


    // Reset password
    GlobalResponseDTO<NoPaginatedMeta, BlankData> resetPasswordOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> resetPasswordListUsers(String userIds);
}
