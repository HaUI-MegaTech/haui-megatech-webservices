package shop.haui_megatech.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.repository.UserRepository;

import java.text.DecimalFormat;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findActiveUserByUsername(username)
                                         .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DecimalFormat decimalFormat() {
        return new DecimalFormat();
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            String newPassword = Integer.toString((int)(Math.random() * 1e6));

            System.out.println(Integer.toString((int)(Math.random() * 1e6)));
            System.out.println(Integer.toString((int)(Math.random() * 1e6)));
            System.out.println(Integer.toString((int)(Math.random() * 1e6)));
            System.out.println(Integer.toString((int)(Math.random() * 1e6)));
        };
    }
}
