package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.RequestIdDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequestDTO;
import shop.haui_megatech.service.base.*;

public interface ProductService extends Addable<AddProductRequestDTO>,
                                        Gettable<ProductDTO>,
                                        Hidable<ProductDTO>,
                                        Importable,
                                        SoftDeletable<ProductDTO>,
                                        HardDeletable<RequestIdDTO>,
                                        Updatable<UpdateProductRequestDTO> {
    CommonResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request);

    PaginationResponseDTO<ProductDTO> getActiveListByBrand(PaginationRequestDTO request, Integer brandId);
}
