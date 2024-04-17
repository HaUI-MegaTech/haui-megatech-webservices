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
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequest;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequest;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.exception.*;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.util.MessageSourceUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.util.List;
import java.util.Objects;
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

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        return CommonResponseDTO.<UserDTO>builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.FOUND))
                                .item(mapper.toUserDTO(foundUser.get()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> createUser(CreateUserRequestDTO request) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessageConstant.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessageConstant.Request.BLANK_PASSWORD);

        if (!request.password().equalsIgnoreCase(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessageConstant.User.MISMATCHED_PASSWORD);

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
            UpdateUserInfoRequest request
    ) {
        if (Objects.isNull(request))
            throw new NullRequestException(ErrorMessageConstant.Request.NULL_REQUEST);

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();

        if (request.firstName() != null) foundUser.setFirstName(request.firstName());
        if (request.lastName() != null) foundUser.setLastName(request.lastName());
        if (request.avatar() != null) foundUser.setAvatar(request.avatar());
        if (request.email() != null) foundUser.setEmail(request.email());
        if (request.phoneNumber() != null) foundUser.setPhoneNumber(request.phoneNumber());

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
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();
        if (!foundUser.getPassword().equals(passwordEncoder.encode(request.oldPassword())))
            throw new WrongPasswordException(ErrorMessageConstant.User.WRONG_PASSWORD);

        if (!request.newPassword().equals(request.confirmNewPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessageConstant.User.MISMATCHED_PASSWORD);

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
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

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
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

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
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        found.get().setDeleted(false);

        return CommonResponseDTO.builder()
                                .result(true)
                                .message(SuccessMessageConstant.User.RESTORED)
                                .build();
    }

    @Override
    public PaginationResponseDTO<UserDTO> getActiveUsers(PaginationRequestDTO request) {
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

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
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

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
