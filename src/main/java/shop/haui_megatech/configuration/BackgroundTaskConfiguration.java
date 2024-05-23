package shop.haui_megatech.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.haui_megatech.domain.entity.Token;
import shop.haui_megatech.repository.TokenRepository;
import shop.haui_megatech.service.LoginStatisticService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class BackgroundTaskConfiguration {
    private final LoginStatisticService loginStatisticService;
    private final TokenRepository       tokenRepository;

    @Bean
    public CommandLineRunner launchLoginStatisticService() {
        return args -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(loginStatisticService::saveOrUpdate, 0, 5, TimeUnit.MINUTES);
        };
    }

    @Bean
    public CommandLineRunner launchTokenCleanerService() {
        return args -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                List<Token> list = tokenRepository.findAll();
                Date now = new Date();
                tokenRepository.deleteAll(
                        list.parallelStream()
                            .filter(item -> item.getWhenExpired().after(now))
                            .toList()
                );
            }, 0, 1, TimeUnit.HOURS);
        };
    }
}
