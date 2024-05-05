package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.user.AddUserRequestDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.AuthenticationService;
import shop.haui_megatech.utility.JwtTokenUtil;

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
        User user = User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .build();
        userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponseDTO.builder()
                                        .token(jwtToken)
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
        return AuthenticationResponseDTO.builder()
                                        .token(jwtToken)
                                        .build();
    }

    @Override
    public AuthenticationResponseDTO refresh(AuthenticationRequestDTO request) {
        Optional<User> foundUser = userRepository.findActiveUserByUsername(request.username());

        if (foundUser.isEmpty())
            throw new UsernameNotFoundException(request.username());

        if (!passwordEncoder.matches(request.password(), foundUser.get().getPassword()))
            throw new BadCredentialsException(request.password());

        return AuthenticationResponseDTO.builder()
                                        .token(jwtUtil.generateToken(User.builder()
                                                                         .username(request.username())
                                                                         .build()))
                                        .build();
    }


}
