package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.product.*;

import java.util.List;

public interface ProductService {
    // Get
    GlobalResponseDTO<NoPaginatedMeta, FullProductResponseDTO> getOne(Integer id);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getList(PaginationRequestDTO request, FilterProductRequestDTO filter);


    // Add
    GlobalResponseDTO<NoPaginatedMeta, BriefProductResponseDTO> addOne(AddProductRequestDTO request);


    // Update
    GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer id, UpdateProductRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> updateListFromExcel(ImportDataRequestDTO request);


    // Hide
    GlobalResponseDTO<NoPaginatedMeta, BlankData> hideOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> hideList(ListIdsRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getHiddenList(PaginationRequestDTO request);


    // Expose
    GlobalResponseDTO<NoPaginatedMeta, BlankData> exposeOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> exposeList(ListIdsRequestDTO request);

    // Soft delete
    GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteList(ListIdsRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getDeletedList(PaginationRequestDTO request);


    // Restore
    GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreList(ListIdsRequestDTO request);


    // Hard delete
    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteList(ListIdsRequestDTO request);


    // Import
    GlobalResponseDTO<NoPaginatedMeta, BlankData> importExcel(ImportDataRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> importCsv(ImportDataRequestDTO request);
}
