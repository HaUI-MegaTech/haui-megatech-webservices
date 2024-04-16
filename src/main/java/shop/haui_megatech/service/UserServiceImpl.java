package shop.haui_megatech.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.util.MessageSourceUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository    userRepository;
    private final MessageSourceUtil messageSourceUtil;
    private final PasswordEncoder   passwordEncoder;
    private final UserMapper        mapper;

    @Override
    public CommonResponseDTO<UserDTO> getUserById(Integer userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        return foundUser.isPresent()
                ? CommonResponseDTO.<UserDTO>builder()
                                   .result(true)
                                   .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.FOUND))
                                   .item(mapper.toUserDTO(foundUser.get()))
                                   .build()
                : CommonResponseDTO.<UserDTO>builder()
                                   .result(false)
                                   .message(messageSourceUtil.getMessage(ErrorMessageConstant.Product.NOT_FOUND))
                                   .item(null)
                                   .build();
    }

    @Override
    public CommonResponseDTO<?> createUser(CreateUserRequestDTO request) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.Request.BLANK_USERNAME))
                                    .build();

        if (!RequestValidator.isBlankRequestParams(request.password()))
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.Request.BLANK_PASSWORD))
                                    .build();

        if (request.password().equalsIgnoreCase(request.confirmPassword()))
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.MISMATCHED_PASSWORD))
                                    .build();

        return CommonResponseDTO.<UserDTO>builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.CREATED))
                                .item(mapper.toUserDTO(
                                        userRepository.save(User.builder()
                                                                .username(request.username())
                                                                .password(passwordEncoder.encode(request.password()))
                                                                .firstName(request.firstName())
                                                                .lastName(request.lastName())
                                                                .build()
                                        )))
                                .build();

    }

    @Override
    public CommonResponseDTO<?> updateUserInfo(
            Integer userId,
            UserDTO dto
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.NOT_FOUND))
                                    .build();

        User foundUser = found.get();
        if (dto.firstName() != null) foundUser.setFirstName(dto.firstName());
        if (dto.lastName() != null) foundUser.setLastName(dto.lastName());
        if (dto.avatar() != null) foundUser.setAvatar(dto.avatar());
        if (dto.email() != null) foundUser.setEmail(dto.email());
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.INFO_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> updateUserPassword(
            Integer userId,
            UpdateUserPasswordRequest request
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.NOT_FOUND))
                                    .build();

        User foundUser = found.get();
        if (!foundUser.getPassword().equals(passwordEncoder.encode(request.oldPassword())))
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.WRONG_PASSWORD))
                                    .build();

        if (!request.newPassword().equals(request.confirmNewPassword()))
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.MISMATCHED_PASSWORD))
                                    .build();

        foundUser.setPassword(passwordEncoder.encode(request.newPassword()));

        return CommonResponseDTO.builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.PASSWORD_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> temporarilyDeleteUserById(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.NOT_FOUND))
                                    .build();

        User foundUser = found.get();
        foundUser.setDeleted(true);
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.TEMPORARILY_DELETED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> permanentlyDeleteUserById(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.NOT_FOUND))
                                    .build();

        userRepository.delete(found.get());
        return CommonResponseDTO.builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.PERMANENTLY_DELETED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreDeletedUserById(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(messageSourceUtil.getMessage(ErrorMessageConstant.User.NOT_FOUND))
                                    .build();

        found.get().setDeleted(false);
        return CommonResponseDTO.builder()
                                .result(true)
                                .message(SuccessMessageConstant.User.RESTORED)
                                .build();
    }

    @Override
    public PaginationResponseDTO<UserDTO> getActiveUsers(PaginationRequestDTO request) {
        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                ? Sort.by(request.orderBy())
                      .ascending()
                : Sort.by(request.orderBy())
                      .descending();
        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<User> page = request.keyword() == null
                ? userRepository.getAllActiveUsers(pageable)
                : userRepository.searchActiveUsers(request.keyword(), pageable);
        List<User> users = page.getContent();

        return PaginationResponseDTO.<UserDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(users.parallelStream()
                                                .map(mapper::toUserDTO)
                                                .collect(Collectors.toList()))
                                    .build();
    }

    @Override
    public PaginationResponseDTO<UserDTO> getDeletedUsers(PaginationRequestDTO request) {
        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                ? Sort.by(request.orderBy())
                      .ascending()
                : Sort.by(request.orderBy())
                      .descending();
        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<User> page = request.keyword() == null
                ? userRepository.getAllDeletedUsers(pageable)
                : userRepository.searchDeletedUsers(request.keyword(), pageable);
        List<User> users = page.getContent();

        return PaginationResponseDTO.<UserDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(users.parallelStream()
                                                .map(mapper::toUserDTO)
                                                .collect(Collectors.toList()))
                                    .build();
    }
}
