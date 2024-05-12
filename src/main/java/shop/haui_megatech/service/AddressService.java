package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.address.AddressRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface AddressService {
    CommonResponseDTO<?> addOne(Integer userId, AddressRequestDTO request);

    CommonResponseDTO<?> updateOne(Integer userId, Integer addressId, AddressRequestDTO request);

    CommonResponseDTO<?> delete(Integer userId, String addressIds);
}
