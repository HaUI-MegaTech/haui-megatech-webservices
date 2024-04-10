package shop.haui_megatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.authentication.AuthenticationRequestDTO;
import shop.haui_megatech.domain.dto.authentication.AuthenticationResponseDTO;
import shop.haui_megatech.domain.dto.authentication.RegisterRequestDTO;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.util.JwtUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
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
        User user = repository.findByUsername(request.username()).orElseThrow();
        String jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponseDTO.builder()
                                        .token(jwtToken)
                                        .build();
    }
}
