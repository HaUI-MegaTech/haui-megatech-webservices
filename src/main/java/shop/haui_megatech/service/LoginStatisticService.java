package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.home.LoginStatisticResponseDTO;

import java.util.List;

public interface LoginStatisticService {
    void saveOrUpdate();

    GlobalResponseDTO<NoPaginatedMeta, List<LoginStatisticResponseDTO>> getListByDay();
}
