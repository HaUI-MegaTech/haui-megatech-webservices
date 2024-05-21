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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.LoginStatisticService;
import shop.haui_megatech.utility.FakeDataGenerator;
import shop.haui_megatech.utility.MyCustomUtil;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepository        userRepository;
    private final LoginStatisticService loginStatisticService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findActiveUserByUsername(username)
                                         .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND));
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

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//        return args -> externalCrawler.crawl();
//    }

    @Bean
    public CommandLineRunner commandLineRunner(FakeDataGenerator fakeDataGenerator, MyCustomUtil myCustomUtil) {
        return args -> {
//            fakeDataGenerator.fakeProductData();
//            myCustomUtil.calculateProductRatingAverage();
//            myCustomUtil.countProductFeedbacks();
        };
    }

    @Bean
    public CommandLineRunner launchLoginStatisticService() {
        return args -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(loginStatisticService::saveOrUpdate, 0, 5, TimeUnit.MINUTES);
        };
    }
}
