package shop.haui_megatech.domain.dto.common;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public record ImportDataRequestDTO(
        @RequestParam(name = "file") MultipartFile file
) {
}
