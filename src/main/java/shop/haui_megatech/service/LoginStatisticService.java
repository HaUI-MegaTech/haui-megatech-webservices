package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.home.LoginStatisticResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

import java.util.List;

public interface LoginStatisticService {
    void saveOrUpdate();

    GlobalResponseDTO<List<LoginStatisticResponseDTO>> getListByDay();
}
