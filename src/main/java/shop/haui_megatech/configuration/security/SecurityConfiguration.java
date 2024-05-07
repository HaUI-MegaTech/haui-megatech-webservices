package shop.haui_megatech.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.haui_megatech.configuration.security.filter.JwtAuthenticationFilter;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final String                  CATCH_ALL_WILDCARDS = "/**";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider  authenticationProvider;
    private final String[]                WHITE_LIST_URLS     = {
            "/swagger-ui" + CATCH_ALL_WILDCARDS,
            UrlConstant.API_V1 + UrlConstant.Auth.PREFIX + CATCH_ALL_WILDCARDS,
            "/v3/api-docs" + CATCH_ALL_WILDCARDS,
            UrlConstant.API_V1 + UrlConstant.Product.GET_ACTIVE_LIST,
            UrlConstant.API_V1 + UrlConstant.Product.GET_ACTIVE_LIST_BY_BRAND,
            UrlConstant.API_V1 + UrlConstant.Product.GET_DETAIL_ONE,
            UrlConstant.API_V1 + UrlConstant.Brand.GET_ONE,
            UrlConstant.API_V1 + UrlConstant.Brand.GET_ACTIVE_LIST,
            "/search",
            UrlConstant.API_V1 + UrlConstant.Order.PREFIX + CATCH_ALL_WILDCARDS
    };
    private final List<String>            WHITE_LIST_ORIGINS  = List.of(
            "http://localhost:3000",
            "http://localhost:3001"
    );

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MessageSourceUtil messageSourceUtil)
            throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
                   .authorizeHttpRequests(
                           auth -> auth.requestMatchers(WHITE_LIST_URLS)
                                       .permitAll()
                                       .anyRequest()
                                       .authenticated()
                   )
                   .sessionManagement(
                           management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   )
                   .authenticationProvider(authenticationProvider)
                   .exceptionHandling(
                           httpSecurityExceptionHandlingConfigurer -> {
                               httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                       (request, response, authException) -> {
                                           response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                           response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                                           CommonResponseDTO<?> responseBody =
                                                   CommonResponseDTO.builder()
                                                                    .success(false)
                                                                    .message(messageSourceUtil.getMessage(
                                                                            ErrorMessageConstant.Auth.AUTHENTICATE
                                                                    ))
                                                                    .build();

                                           ObjectMapper objectMapper = new ObjectMapper();
                                           response.getWriter().write(objectMapper.writeValueAsString(responseBody));
                                       }
                               );
                           }
                   )
                   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(WHITE_LIST_ORIGINS);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Accept-Language", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization", "Accept-Language"));
        configuration.setMaxAge((long) (24 * 60 * 60));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
