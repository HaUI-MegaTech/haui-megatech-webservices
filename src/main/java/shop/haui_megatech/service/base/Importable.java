package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface Importable {
    GlobalResponseDTO<?> importExcel(ImportDataRequestDTO request);

    GlobalResponseDTO<?> importCsv(ImportDataRequestDTO request);
}
