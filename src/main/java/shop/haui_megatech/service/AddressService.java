package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.address.AddressRequestDTO;
import shop.haui_megatech.domain.dto.address.AddressResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

import java.util.List;

public interface AddressService {
    GlobalResponseDTO<?> addOne(Integer userId, AddressRequestDTO request);

    GlobalResponseDTO<?> updateOne(Integer userId, Integer addressId, AddressRequestDTO request);

    GlobalResponseDTO<?> delete(Integer userId, String addressIds);

    GlobalResponseDTO<List<AddressResponseDTO>> getAllByUserId(Integer userId);
}
