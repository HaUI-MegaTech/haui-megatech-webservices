package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.BriefProductResponseDTO;
import shop.haui_megatech.domain.dto.product.FilterProductRequestDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequestDTO;
import shop.haui_megatech.service.base.*;

public interface ProductService extends Addable<AddProductRequestDTO>,
                                        Hidable<BriefProductResponseDTO>,
                                        Importable,
                                        SoftDeletable<BriefProductResponseDTO>,
                                        HardDeletable,
                                        Updatable<UpdateProductRequestDTO> {
    CommonResponseDTO<?> getOne(Integer id);

    PaginationResponseDTO<BriefProductResponseDTO> getList(PaginationRequestDTO request, FilterProductRequestDTO filter);

    CommonResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request);
}
