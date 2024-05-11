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
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.SimilarProduct;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.repository.SimilarProductRepository;
import shop.haui_megatech.repository.UserRepository;

import java.text.DecimalFormat;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepository           userRepository;
    private final ProductRepository        productRepository;
    private final SimilarProductRepository similarProductRepository;

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
    public CommandLineRunner commandLineRunner() {
        List<Product> list = productRepository.findAll();

        return args -> {
            // cao xong 100 phan tu thi doi api + nhich len 101 - 200
            for (int i = 1; i <= 2; i++) {
                final Integer index = i;
                new Thread(() -> {
                    list.get(index).getName();
                    // trich xuat ra ma

                    // code call api ve

                    // luu similar product

                    // hung ket qua


                    // access vao list
                    // hung gia tri tu cai SearchResult tra ve

                    // loop list

                    // for
                    SimilarProduct similarProduct = new SimilarProduct();
                    similarProduct.setProduct(list.get(index));
                    similarProduct.setProductUrl("Test product url");
                    similarProduct.setProductName("Test product name");
                    similarProduct.setProductImageUrl("Test product image url");
                    similarProduct.setPrice(10.0F);
                    similarProduct.setProduct(list.get(index));
                    SimilarProduct saved = similarProductRepository.save(
                            similarProduct
                    );
                    System.out.println("Completed " + index);
                    // bao gio hien la Completed 100 || 200 || 205
                    // end for
                }).start();
            }
        };
    }
}
