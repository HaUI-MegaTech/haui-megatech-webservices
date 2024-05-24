package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.address.AddressRequestDTO;
import shop.haui_megatech.domain.dto.address.AddressResponseDTO;
import shop.haui_megatech.domain.dto.global.BlankData;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;

import java.util.List;

public interface AddressService {
    GlobalResponseDTO<NoPaginatedMeta, BlankData> addOne(Integer userId, AddressRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer userId, Integer addressId, AddressRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> delete(Integer userId, String addressIds);

    GlobalResponseDTO<NoPaginatedMeta, List<AddressResponseDTO>> getAllByUserId(Integer userId);
}
