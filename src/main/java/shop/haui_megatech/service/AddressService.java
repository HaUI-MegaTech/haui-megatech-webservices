package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface AddressService {
    CommonResponseDTO<?> addOne(Integer userId, AddressDTO.Request request);

    CommonResponseDTO<?> updateOne(Integer userId, Integer addressId, AddressDTO.Request request);

    CommonResponseDTO<?> delete(Integer userId, String addressIds);
}
