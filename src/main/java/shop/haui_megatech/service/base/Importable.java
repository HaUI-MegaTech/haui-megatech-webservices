package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequest;

public interface Importable {
    CommonResponseDTO<?> importExcel(ImportDataRequest request);

    CommonResponseDTO<?> importCsv(ImportDataRequest request);
}
