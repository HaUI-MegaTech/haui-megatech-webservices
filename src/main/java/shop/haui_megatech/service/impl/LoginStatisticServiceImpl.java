package shop.haui_megatech.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.entity.LoginStatistic;
import shop.haui_megatech.repository.LoginStatisticRepository;
import shop.haui_megatech.service.LoginStatisticService;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginStatisticServiceImpl implements LoginStatisticService {
    public static Integer counter;
    public static Boolean modified;

    private final LoginStatisticRepository loginStatisticRepository;

    @PostConstruct
    public void postConstruct() {
        modified = false;
        counter = 0;
    }

    @Override
    @PreDestroy
    public void saveOrUpdate() {
        if (!modified) return;

        Optional<LoginStatistic> found = loginStatisticRepository.findById(new Date());
        found.ifPresentOrElse(
                (item) -> {
                    item.setLoggedIn(item.getLoggedIn() + counter);
                    item.setLastUpdated(new Date(Instant.now().toEpochMilli()));
                    loginStatisticRepository.save(item);
                },
                () -> {
                    loginStatisticRepository.save(
                            LoginStatistic.builder()
                                          .date(new Date())
                                          .loggedIn(counter)
                                          .lastUpdated(new Date(Instant.now().toEpochMilli()))
                                          .build()
                    );
                }
        );
        postConstruct();
    }
}
