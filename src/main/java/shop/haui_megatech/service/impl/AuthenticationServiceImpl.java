package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.entity.enums.Role;
import shop.haui_megatech.domain.mapper.UserMapper;
import shop.haui_megatech.exception.AbsentRequiredFieldException;
import shop.haui_megatech.exception.DuplicateUsernameException;
import shop.haui_megatech.exception.MismatchedConfirmPasswordException;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.JwtTokenUtil;
import shop.haui_megatech.utility.MessageSourceUtil;
import shop.haui_megatech.validator.RequestValidator;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil          jwtUtil;
    private final MessageSourceUtil     messageSourceUtil;

    @Override
    public AuthenticationResponseDTO register(AddUserRequestDTO request) {
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
                        .phoneNumber(request.phoneNumber())
                        .role(Role.CUSTOMER)
                        .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponseDTO
                .builder()
                .success(true)
                .token(jwtToken)
                .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(savedUser))
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        User user = userRepository.findActiveUserByUsername(request.username()).orElseThrow();

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
        return AuthenticationResponseDTO
                .builder()
                .success(true)
                .message(messageSourceUtil.getMessage(SuccessMessage.Auth.AUTHENTICATED))
                .token(jwtToken)
                .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(updatedUser))
                .build();
    }

    @Override
    public AuthenticationResponseDTO refresh(String token) {
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findUserByUsername(username).orElseThrow();
        String newAccessToken = jwtUtil.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .success(true)
                .token(newAccessToken)
                .build();
    }


}
