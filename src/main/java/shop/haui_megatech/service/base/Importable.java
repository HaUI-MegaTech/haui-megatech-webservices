package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;

public interface Importable {
    CommonResponseDTO<?> importExcel(ImportDataRequestDTO request);

    CommonResponseDTO<?> importCsv(ImportDataRequestDTO request);
}
