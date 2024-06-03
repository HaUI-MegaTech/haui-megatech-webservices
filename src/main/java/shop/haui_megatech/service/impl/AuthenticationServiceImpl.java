package shop.haui_megatech.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.RefreshTokenRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.entity.Token;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.entity.enums.Role;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.exception.*;
import shop.haui_megatech.repository.TokenRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.JwtTokenUtil;
import shop.haui_megatech.utility.MessageSourceUtil;
import shop.haui_megatech.utility.NetworkUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository        userRepository;
    private final TokenRepository       tokenRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil          jwtUtil;
    private final MessageSourceUtil     messageSourceUtil;

    private Token generateRefreshToken(HttpServletRequest request, User owner) {
        return tokenRepository.save(
                Token.builder()
                     .ipAddress(NetworkUtil.getClientIpAddress(request))
                     .userAgent(NetworkUtil.getUserAgent(request))
                     .whenCreated(new Date(Instant.now().toEpochMilli()))
                     .whenExpired(new Date(
                             Instant.now()
                                    .plus(7, ChronoUnit.DAYS)
                                    .toEpochMilli()
                     ))
                     .owner(owner)
                     .build()
        );
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> register(AddUserRequestDTO request, HttpServletRequest servletRequest) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_PASSWORD);

        if (!request.password().equals(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        if (userRepository.findUserByUsername(request.username()).isPresent())
            throw new DuplicateUsernameException(ErrorMessage.Request.DUPLICATE_USERNAME);

        User user = User.builder()
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
//                        .phoneNumber(request.phoneNumber())
                        .role(Role.CUSTOMER)
                        .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(user);

        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                              .builder()
                              .status(Status.SUCCESS)
                              .message(messageSourceUtil.getMessage(SuccessMessage.Auth.REGISTERED))
                              .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .refreshToken(generateRefreshToken(servletRequest, savedUser).getId())
                              .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(savedUser))
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> authenticate(AuthenticationRequestDTO request, HttpServletRequest servletRequest) {
        User user = userRepository.findActiveUserByUsername(request.username())
                                  .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String jwtToken = jwtUtil.generateToken(user);
        user.setLastLoggedIn(new Date(Instant.now().toEpochMilli()));
        user.setLoggedIn(user.getLoggedIn() == null ? 1 : user.getLoggedIn() + 1);
        User updatedUser = userRepository.save(user);

        LoginStatisticServiceImpl.modified = true;
        ++LoginStatisticServiceImpl.counter;

        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                              .builder()
                              .status(Status.SUCCESS)
                              .message(messageSourceUtil.getMessage(SuccessMessage.Auth.AUTHENTICATED))
                              .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .refreshToken(generateRefreshToken(servletRequest, updatedUser).getId())
                              .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(updatedUser))
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> refresh(RefreshTokenRequestDTO request, HttpServletRequest servletRequest) {
        Optional<Token> found = tokenRepository.findById(request.refreshToken());
        if (found.isEmpty())
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        Token token = found.get();

        if (token.getWhenExpired().before(new Date()))
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        if (!token.getUserAgent().equals(NetworkUtil.getUserAgent(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_USER_AGENT);

        if (!token.getIpAddress().equals(NetworkUtil.getClientIpAddress(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_IP_ADDRESS);

        token.setWhenExpired(new Date(Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()));
        tokenRepository.save(token);
        User user = token.getOwner();
        String jwtToken = jwtUtil.generateToken(user);
        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                              .builder()
                              .status(Status.SUCCESS)
                              .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> logout(RefreshTokenRequestDTO request, HttpServletRequest servletRequest) {
        Optional<Token> found = tokenRepository.findById(request.refreshToken());
        if (found.isEmpty())
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        Token token = found.get();

        if (token.getWhenExpired().before(new Date()))
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        if (!token.getUserAgent().equals(NetworkUtil.getUserAgent(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_USER_AGENT);

        if (!token.getIpAddress().equals(NetworkUtil.getClientIpAddress(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_IP_ADDRESS);

        tokenRepository.deleteById(request.refreshToken());

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                              .builder()
                              .status(Status.SUCCESS)
                              .message(messageSourceUtil.getMessage(SuccessMessage.Auth.LOGGED_OUT))
                              .build()
                )
                .build();
    }


}
