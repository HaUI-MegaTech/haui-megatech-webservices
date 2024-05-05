package shop.haui_megatech.service.impl;

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
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserInfoRequestDTO;
import shop.haui_megatech.domain.dto.user.UpdateUserPasswordRequestDTO;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.exception.*;
import shop.haui_megatech.job.AutoMailSender;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.UserService;
import shop.haui_megatech.utility.CsvUtil;
import shop.haui_megatech.utility.ExcelUtil;
import shop.haui_megatech.utility.MessageSourceUtil;
import shop.haui_megatech.utility.RandomUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.io.IOException;
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
    private final AutoMailSender    autoMailSender;

    @Override
    public CommonResponseDTO<UserDTO> getOne(Integer userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        return CommonResponseDTO.<UserDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.FOUND))
                                .item(UserMapper.INSTANCE.toUserDTO(foundUser.get()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> addOne(AddUserRequestDTO request) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessageConstant.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessageConstant.Request.BLANK_PASSWORD);

        if (!request.password().equals(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessageConstant.User.MISMATCHED_PASSWORD);

        if (userRepository.findUserByUsername(request.username()).isPresent())
            throw new DuplicateUsernameException(ErrorMessageConstant.Request.DUPLICATE_USERNAME);

        return CommonResponseDTO.<UserDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.ADDED_ONE))
                                .item(UserMapper.INSTANCE.toUserDTO(
                                        userRepository.save(User.builder()
                                                                .username(request.username())
                                                                .password(passwordEncoder.encode(request.password()))
                                                                .firstName(request.firstName())
                                                                .lastName(request.lastName())
                                                                .build())))
                                .build();

    }

    @Override
    public CommonResponseDTO<?> importExcel(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessageConstant.Request.MALFORMED_FILE);

        try {
            List<User> users = ExcelUtil.excelToUsers(request.file().getInputStream());
            List<User> savedUsers = userRepository.saveAll(users);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.IMPORTED_LIST,
                                            savedUsers.size()))
                                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Excel data is failed to store: " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> importCsv(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessageConstant.Request.MALFORMED_FILE);

        try {
            List<User> users = CsvUtil.csvToUsers(request.file().getInputStream());
            List<User> savedUsers = userRepository.saveAll(users);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.IMPORTED_LIST,
                                            savedUsers.size()))
                                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Data is not store successfully: " + ex.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> updateOne(
            Integer userId,
            UpdateUserInfoRequestDTO request
    ) {
        if (Objects.isNull(request))
            throw new NullRequestException(ErrorMessageConstant.Request.NULL_REQUEST);

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();

        if (request.firstName() != null) foundUser.setFirstName(request.firstName());
        if (request.lastName() != null) foundUser.setLastName(request.lastName());
        if (request.avatar() != null) foundUser.setAvatarImageUrl(request.avatar());
        if (request.email() != null) foundUser.setEmail(request.email());
        if (request.phoneNumber() != null) foundUser.setPhoneNumber(request.phoneNumber());

        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.INFO_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> updatePassword(
            Integer userId,
            UpdateUserPasswordRequestDTO request
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();

        if (!passwordEncoder.matches(request.oldPassword(), foundUser.getPassword()))
            throw new WrongPasswordException(ErrorMessageConstant.User.WRONG_PASSWORD);

        if (!request.newPassword().equals(request.confirmNewPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessageConstant.User.MISMATCHED_PASSWORD);

        foundUser.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.PASSWORD_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteOne(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setDeleted(true);
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.SOFT_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request) {
        List<User> foundUsers = userRepository.findAllById(request.ids());

        foundUsers.parallelStream().forEach(item -> item.setDeleted(true));

        userRepository.saveAll(foundUsers);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.SOFT_DELETED_LIST,
                                        foundUsers.size()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteOne(Integer id) {
        Optional<User> found = userRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        userRepository.delete(found.get());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.HARD_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        userRepository.deleteAllById(request.ids());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.HARD_DELETED_LIST,
                                        request.ids().size()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreOne(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        found.get().setDeleted(false);
        userRepository.save(found.get());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.RESTORED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreList(ListIdsRequestDTO request) {
        List<User> foundUsers = userRepository.findAllById(request.ids());

        foundUsers.parallelStream().forEach(item -> item.setDeleted(false));

        userRepository.saveAll(foundUsers);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.RESTORED_LIST,
                                        foundUsers.size()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> resetPasswordOne(Integer userId) {
        String newPassword = RandomUtil.randomPassword();

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(foundUser);
        autoMailSender.sendResetPasswordMail(foundUser.getEmail(), newPassword);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.RESET_PASSWORD_ONE,
                                        foundUser.getUsername()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> resetPasswordList(ListIdsRequestDTO request) {
        List<User> foundUsers = userRepository.findAllById(request.ids());

        foundUsers.parallelStream().forEach(item -> {
            this.resetPasswordOne(item.getId());
        });

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.User.RESET_PASSWORD_LIST,
                                        foundUsers.size()))
                                .build();
    }

    @Override
    public PaginationResponseDTO<UserDTO> getList(PaginationRequestDTO request) {
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
                                    .pageSize((short) page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(users.parallelStream()
                                                .map(UserMapper.INSTANCE::toUserDTO)
                                                .collect(Collectors.toList()))
                                    .build();
    }

    @Override
    public PaginationResponseDTO<UserDTO> getDeletedList(PaginationRequestDTO request) {
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
                                    .pageSize((short) page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(users.parallelStream()
                                                .map(UserMapper.INSTANCE::toUserDTO)
                                                .collect(Collectors.toList()))
                                    .build();
    }
}
