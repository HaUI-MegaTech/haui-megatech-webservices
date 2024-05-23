package shop.haui_megatech.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.haui_megatech.domain.entity.LoginStatistic;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.repository.LoginStatisticRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.utility.FakeDataGenerator;
import shop.haui_megatech.utility.MyCustomUtil;
import shop.haui_megatech.utility.RandomUtil;
import shop.haui_megatech.utility.RandomValueGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FakeDataConfiguration {
    private final LoginStatisticRepository loginStatisticRepository;
    private final ProductRepository        productRepository;


    //    @Bean
//    public CommandLineRunner commandLineRunner() {
//        return args -> externalCrawler.crawl();
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(FakeDataGenerator fakeDataGenerator, MyCustomUtil myCustomUtil) {
//        return args -> {
////            fakeDataGenerator.fakeProductData();
////            myCustomUtil.calculateProductRatingAverage();
////            myCustomUtil.countProductFeedbacks();
//        };
//    }

//    @Bean
//    public CommandLineRunner fakeLoginStatisticData() {
//        return args -> {
//            for (int i = 1; i <= 30; i++) {
//                loginStatisticRepository.save(
//                        LoginStatistic.builder()
//                                      .date(new Date(Instant.now().minus(i, ChronoUnit.DAYS).toEpochMilli()))
//                                      .loggedIn(RandomValueGenerator.getRandomValue(50, 400))
//                                      .build()
//                );
//            }
//        };
//    }

//    @Bean
//    public CommandLineRunner fakeProductViews() {
//        return args -> {
//            List<Product> list = productRepository.findAll();
//            list.forEach(item -> item.setTotalViews(RandomValueGenerator.getRandomValue(5000, 20000)));
//            productRepository.saveAll(list);
//        };
//    }
}
