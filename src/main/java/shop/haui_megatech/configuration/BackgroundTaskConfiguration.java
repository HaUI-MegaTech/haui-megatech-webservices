package shop.haui_megatech.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.haui_megatech.service.LoginStatisticService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class BackgroundTaskConfiguration {
    private final LoginStatisticService loginStatisticService;

    @Bean
    public CommandLineRunner launchLoginStatisticService() {
        return args -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(loginStatisticService::saveOrUpdate, 0, 5, TimeUnit.MINUTES);
        };
    }
}
