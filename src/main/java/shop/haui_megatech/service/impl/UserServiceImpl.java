package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.UserDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.exception.*;
import shop.haui_megatech.job.AutoMailSender;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.FileUploadService;
import shop.haui_megatech.service.UserService;
import shop.haui_megatech.utility.CsvUtil;
import shop.haui_megatech.utility.ExcelUtil;
import shop.haui_megatech.utility.MessageSourceUtil;
import shop.haui_megatech.utility.RandomUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
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
    private final FileUploadService fileUploadService;

    @Override
    public CommonResponseDTO<UserDTO.DetailResponse> getOne(Integer userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        return CommonResponseDTO.<UserDTO.DetailResponse>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.FOUND))
                                .item(UserMapper.INSTANCE.toUserDetailDTO(foundUser.get()))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> addOne(UserDTO.AddRequest request) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_PASSWORD);

        if (!request.password().equals(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        if (userRepository.findUserByUsername(request.username()).isPresent())
            throw new DuplicateUsernameException(ErrorMessage.Request.DUPLICATE_USERNAME);

        return CommonResponseDTO.<UserDTO.Response>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.ADDED_ONE))
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
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<User> users = ExcelUtil.excelToUsers(request.file().getInputStream());
            List<User> savedUsers = userRepository.saveAll(users);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(
                                            SuccessMessage.User.IMPORTED_LIST,
                                            savedUsers.size()
                                    ))
                                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Excel data is failed to store: " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> importCsv(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<User> users = CsvUtil.csvToUsers(request.file().getInputStream());
            List<User> savedUsers = userRepository.saveAll(users);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(
                                            SuccessMessage.User.IMPORTED_LIST,
                                            savedUsers.size()
                                    ))
                                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Data is not store successfully: " + ex.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> updateOne(
            Integer userId,
            UserDTO.UpdateInfoRequest request
    ) {
        if (Objects.isNull(request))
            throw new NullRequestException(ErrorMessage.Request.NULL_REQUEST);

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();

        if (request.firstName() != null) foundUser.setFirstName(request.firstName());
        if (request.lastName() != null) foundUser.setLastName(request.lastName());
        if (request.avatar() != null) {
            String avatarImageUrl;
            try {
                avatarImageUrl = fileUploadService.uploadFile(request.avatar());
            } catch (IOException e) {
                throw new RuntimeException("Co loi trong qua trinh luu anh.");
            }
            foundUser.setAvatarImageUrl(avatarImageUrl);
        }
        if (request.email() != null) foundUser.setEmail(request.email());
        if (request.phoneNumber() != null) foundUser.setPhoneNumber(request.phoneNumber());
        foundUser.setLastUpdated(new Date(Instant.now().toEpochMilli()));
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.INFO_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> updatePassword(
            Integer userId,
            UserDTO.UpdatePasswordRequest request
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();

        if (!passwordEncoder.matches(request.oldPassword(), foundUser.getPassword()))
            throw new WrongPasswordException(ErrorMessage.User.WRONG_PASSWORD);

        if (!request.newPassword().equals(request.confirmNewPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        foundUser.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.PASSWORD_UPDATED))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteOne(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setDeleted(true);
        userRepository.save(foundUser);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.SOFT_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request) {
        List<User> foundUsers = userRepository.findAllById(request.ids());

        foundUsers.parallelStream().forEach(item -> item.setDeleted(true));

        userRepository.saveAll(foundUsers);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(
                                        SuccessMessage.User.SOFT_DELETED_LIST,
                                        foundUsers.size()
                                ))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteOne(Integer id) {
        Optional<User> found = userRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        userRepository.delete(found.get());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.HARD_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        userRepository.deleteAllById(request.ids());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(
                                        SuccessMessage.User.HARD_DELETED_LIST,
                                        request.ids().size()
                                ))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreOne(Integer userId) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        found.get().setDeleted(false);
        userRepository.save(found.get());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.User.RESTORED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreList(ListIdsRequestDTO request) {
        List<User> foundUsers = userRepository.findAllById(request.ids());

        foundUsers.parallelStream().forEach(item -> item.setDeleted(false));

        userRepository.saveAll(foundUsers);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(
                                        SuccessMessage.User.RESTORED_LIST,
                                        foundUsers.size()
                                ))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> resetPasswordOne(Integer userId) {
        String newPassword = RandomUtil.randomPassword();

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(foundUser);
        autoMailSender.sendResetPasswordMail(foundUser.getEmail(), newPassword);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(
                                        SuccessMessage.User.RESET_PASSWORD_ONE,
                                        foundUser.getUsername()
                                ))
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
                                .message(messageSourceUtil.getMessage(
                                        SuccessMessage.User.RESET_PASSWORD_LIST,
                                        foundUsers.size()
                                ))
                                .build();
    }

    @Override
    public PaginationDTO.Response<UserDTO.Response> getList(PaginationDTO.Request request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<User> page = request.keyword() == null
                          ? userRepository.getAllActiveUsers(pageable)
                          : userRepository.searchActiveUsers(request.keyword(), pageable);

        List<User> users = page.getContent();

        return PaginationDTO.Response.<UserDTO.Response>builder()
                                     .keyword(request.keyword())
                                     .pageIndex(request.index())
                                     .pageSize((short) page.getNumberOfElements())
                                     .totalItems(page.getTotalElements())
                                     .totalPages(page.getTotalPages())
                                     .items(users.parallelStream()
                                                 .map(UserMapper.INSTANCE::toUserDTO)
                                                 .collect(Collectors.toList()))
                                     .build();
    }

    @Override
    public PaginationDTO.Response<UserDTO.Response> getDeletedList(PaginationDTO.Request request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<User> page = request.keyword() == null
                          ? userRepository.getAllDeletedUsers(pageable)
                          : userRepository.searchDeletedUsers(request.keyword(), pageable);

        List<User> users = page.getContent();

        return PaginationDTO.Response.<UserDTO.Response>builder()
                                     .keyword(request.keyword())
                                     .pageIndex(request.index())
                                     .pageSize((short) page.getNumberOfElements())
                                     .totalItems(page.getTotalElements())
                                     .totalPages(page.getTotalPages())
                                     .items(users.parallelStream()
                                                 .map(UserMapper.INSTANCE::toUserDTO)
                                                 .collect(Collectors.toList()))
                                     .build();
    }
}
