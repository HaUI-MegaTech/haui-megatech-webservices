package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.ProductFilterRequestDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequestDTO;
import shop.haui_megatech.service.base.*;

public interface ProductService extends Addable<AddProductRequestDTO>,
                                        Hidable<ProductDTO>,
                                        Importable,
                                        SoftDeletable<ProductDTO>,
                                        HardDeletable,
                                        Updatable<UpdateProductRequestDTO> {
    CommonResponseDTO<?> getOne(Integer id);

    PaginationResponseDTO<ProductDTO> getList(PaginationRequestDTO request, ProductFilterRequestDTO filter);

    CommonResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request);
}
