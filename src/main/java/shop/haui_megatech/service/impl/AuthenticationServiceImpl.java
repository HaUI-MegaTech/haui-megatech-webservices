package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.dto.auth.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.auth.AuthenticationResponseDTO;
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
                .token(jwtToken)
                .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(savedUser))
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findActiveUserByUsername(request.username()).orElseThrow();
        String jwtToken = jwtUtil.generateToken(user);
        user.setLastLoggedIn(new Date(Instant.now().toEpochMilli()));
        user.setLoggedIn(user.getLoggedIn() == null ? 1 : user.getLoggedIn() + 1);
        User updatedUser = userRepository.save(user);
        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(updatedUser))
                .build();
    }

    @Override
    public AuthenticationResponseDTO refresh(AuthenticationRequestDTO request) {
        Optional<User> foundUser = userRepository.findActiveUserByUsername(request.username());

        if (foundUser.isEmpty())
            throw new UsernameNotFoundException(request.username());

        if (!passwordEncoder.matches(request.password(), foundUser.get().getPassword()))
            throw new BadCredentialsException(request.password());

        return AuthenticationResponseDTO
                .builder()
                .token(jwtUtil.generateToken(User.builder()
                                                 .username(request.username())
                                                 .build()))
                .build();
    }


}
