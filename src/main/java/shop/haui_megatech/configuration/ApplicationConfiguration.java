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
import shop.haui_megatech.job.BrandClassifier;
import shop.haui_megatech.repository.UserRepository;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepository  userRepository;
    private final BrandClassifier brandClassifier;

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
            brandClassifier.classify();
        };
    }

    @Bean
    public DateFormat simpleDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat.
        return dateFormat;
    }
}
