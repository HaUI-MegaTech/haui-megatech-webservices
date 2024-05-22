package shop.haui_megatech.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.home.LoginStatisticResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.entity.LoginStatistic;
import shop.haui_megatech.domain.mapper.LoginStatisticMapper;
import shop.haui_megatech.repository.LoginStatisticRepository;
import shop.haui_megatech.service.LoginStatisticService;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

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

    @Override
    public NoPaginationResponseDTO<LoginStatisticResponseDTO> getListByDay() {
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(0, 7, sort);
        Page<LoginStatistic> page = loginStatisticRepository.findAll(pageable);
        List<LoginStatistic> list = page.getContent();

        return NoPaginationResponseDTO
                .<LoginStatisticResponseDTO>builder()
                .success(true)
                .items(list.parallelStream()
                           .map(LoginStatisticMapper.INSTANCE::toLoginStatisticResponseDTO)
                           .collect(Collectors.toList())
                           .reversed()
                )
                .build();
    }


}
