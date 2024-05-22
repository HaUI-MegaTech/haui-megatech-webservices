package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.home.LoginStatisticResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.entity.LoginStatistic;

public interface LoginStatisticService {
    void saveOrUpdate();

    NoPaginationResponseDTO<LoginStatisticResponseDTO> getListByDay();
}
