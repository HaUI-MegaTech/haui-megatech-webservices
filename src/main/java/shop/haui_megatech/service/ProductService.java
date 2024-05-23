package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.BriefProductResponseDTO;
import shop.haui_megatech.domain.dto.product.FilterProductRequestDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequestDTO;
import shop.haui_megatech.service.base.*;

import java.util.List;

public interface ProductService extends Addable<AddProductRequestDTO>,
                                        Hidable<List<BriefProductResponseDTO>>,
                                        Importable,
                                        SoftDeletable<List<BriefProductResponseDTO>>,
                                        HardDeletable,
                                        Updatable<UpdateProductRequestDTO> {
    GlobalResponseDTO<?> getOne(Integer id);

    GlobalResponseDTO<List<BriefProductResponseDTO>> getList(PaginationRequestDTO request, FilterProductRequestDTO filter);

    GlobalResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request);
}
