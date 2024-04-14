package shop.haui_megatech.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.haui_megatech.configuration.security.filter.JwtAuthenticationFilter;
import shop.haui_megatech.constant.UrlConstant;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final String                  CATCH_ALL_WILDCARDS = "/**";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider  authenticationProvider;
    private final String[]                WHITE_LIST          = {
            "/swagger-ui" + CATCH_ALL_WILDCARDS,
            UrlConstant.API_V1 + UrlConstant.Auth.PREFIX + CATCH_ALL_WILDCARDS,
            "/v3/api-docs" + CATCH_ALL_WILDCARDS,
            UrlConstant.API_V1 + UrlConstant.Product.GET_PRODUCTS,
            UrlConstant.API_V1 + UrlConstant.Product.GET_PRODUCT_BY_ID
    };

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(auth -> auth.anyRequest()
                                                      .permitAll())
                   .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authenticationProvider(authenticationProvider)
                   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
    }


}
