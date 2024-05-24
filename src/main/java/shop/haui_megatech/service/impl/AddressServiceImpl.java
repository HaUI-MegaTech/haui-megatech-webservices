package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.address.AddressRequestDTO;
import shop.haui_megatech.domain.dto.address.AddressResponseDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.entity.Address;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.AddressMapper;
import shop.haui_megatech.exception.AppException;
import shop.haui_megatech.repository.AddressRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.AddressService;
import shop.haui_megatech.utility.AuthenticationUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final UserRepository    userRepository;
    private final AddressRepository addressRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> addOne(Integer userId, AddressRequestDTO request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        if (!Objects.equals(requestedUser.getId(), userId))
            throw new AppException(ErrorMessage.Auth.MALFORMED);
        Address address = AddressMapper.INSTANCE.toAddress(request);
        address.setWhenCreated(new Date(Instant.now().toEpochMilli()));
        address.setUser(requestedUser);
        requestedUser.getAddresses().add(address);
        requestedUser.setLastUpdated(new Date(Instant.now().toEpochMilli()));
        userRepository.save(requestedUser);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Address.ADDED))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer userId, Integer addressId, AddressRequestDTO request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        if (!Objects.equals(requestedUser.getId(), userId))
            throw new AppException(ErrorMessage.Auth.MALFORMED);

        requestedUser.getAddresses()
                     .stream()
                     .filter(item -> item.getId().equals(addressId))
                     .findFirst()
                     .ifPresent(item -> {
                         if (request.province() != null && request.provinceCode() != null) {
                             item.setProvince(request.province());
                             item.setProvinceCode(request.provinceCode());
                         }
                         if (request.district() != null && request.districtCode() != null) {
                             item.setDistrict(request.district());
                             item.setDistrictCode(request.districtCode());
                         }
                         if (request.ward() != null && request.wardCode() != null) {
                             item.setWard(request.ward());
                             item.setWardCode(request.wardCode());
                         }
                         if (request.detail() != null) {
                             item.setDetail(request.detail());
                         }
                         item.setLastUpdated(new Date(Instant.now().toEpochMilli()));
                     });
        requestedUser.setLastUpdated(new Date(Instant.now().toEpochMilli()));
        userRepository.save(requestedUser);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Address.UPDATED))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> delete(Integer userId, String addressIds) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        if (!Objects.equals(requestedUser.getId(), userId))
            throw new AppException(ErrorMessage.Auth.MALFORMED);

        List<Integer> ids = Arrays.stream(addressIds.split(","))
                                  .map(String::trim)
                                  .map(Integer::valueOf)
                                  .toList();

        List<Integer> checkedIds = new ArrayList<>(
                requestedUser.getAddresses()
                             .parallelStream()
                             .map(Address::getId)
                             .filter(ids::contains)
                             .toList()
        );

        addressRepository.deleteAddressByIds(checkedIds);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Address.DELETED, checkedIds.size()))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<AddressResponseDTO>> getAllByUserId(Integer userId) {
        List<Address> list = addressRepository.findAllByUserId(userId);
        return GlobalResponseDTO
                .<NoPaginatedMeta, List<AddressResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(list.stream().map(AddressMapper.INSTANCE::toAddressResponseDTO).toList())
                .build();
    }
}
