package shop.haui_megatech.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.util.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil          jwtUtil;

    @Override
    public AuthenticationResponseDTO register(CreateUserRequestDTO request) {
        User user = User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .build();
        repository.save(user);
        String jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponseDTO.builder()
                                        .token(jwtToken)
                                        .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),
                                                        request.password()
                )
        );
        User user = repository.findActiveUserByUsername(request.username()).orElseThrow();
        String jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponseDTO.builder()
                                        .token(jwtToken)
                                        .build();
    }
}
