package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.ProductDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.service.base.*;

public interface ProductService extends Addable<ProductDTO.AddRequest>,
                                        Hidable<ProductDTO.SummaryResponse>,
                                        Importable,
                                        SoftDeletable<ProductDTO.SummaryResponse>,
                                        HardDeletable,
                                        Updatable<ProductDTO.UpdateRequest> {
    CommonResponseDTO<?> getOne(Integer id);

    PaginationDTO.Response<ProductDTO.SummaryResponse> getList(PaginationDTO.Request request, ProductDTO.FilterRequest filter);

    CommonResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request);
}
