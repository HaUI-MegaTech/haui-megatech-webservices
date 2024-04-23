package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequest;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.service.base.*;

public interface ProductService extends Addable<AddProductRequest>,
                                        Gettable<ProductDTO>,
                                        Hidable<ProductDTO>,
                                        Importable,
                                        SoftDeletable<ProductDTO>,
                                        HardDeletable,
                                        Updatable<UpdateProductRequest> {
    PaginationResponseDTO<ProductDTO> getActiveListByBrand(PaginationRequestDTO request, Integer brandId);
}
