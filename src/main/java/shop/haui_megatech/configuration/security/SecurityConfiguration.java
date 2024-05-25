package shop.haui_megatech.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.dto.global.BlankData;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.global.Status;
import shop.haui_megatech.domain.entity.enums.Authority;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String   CATCH_ALL_WILDCARDS = "/**";
    public static final  String[] PUBLIC_ENDPOINTS    = {
            "/swagger-ui" + CATCH_ALL_WILDCARDS,
            Endpoint.V1.Auth.PREFIX + CATCH_ALL_WILDCARDS,
            "/v3/api-docs" + CATCH_ALL_WILDCARDS,
            Endpoint.V1.Product.GET_ACTIVE_LIST,
            Endpoint.V1.Product.GET_DETAIL_ONE,
            Endpoint.V1.Brand.GET_ONE,
            Endpoint.V1.Brand.GET_ACTIVE_LIST,
            "/api/v1/outer-search",
            "/api/v1/getDataProductByLink",
            "/api/v1/getDataCommentByLink",
            Endpoint.V1.Feedback.GET_LIST_BY_PRODUCT,
            Endpoint.V1.Payment.CALLBACK
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider  authenticationProvider;

    private final List<String> ALLOWED_ORIGINS      = List.of(
            "http://localhost:3000",
            "http://localhost:3001"
    );
    private final List<String> ALLOWED_HTTP_METHODS = List.of(
            GET.toString(),
            POST.toString(),
            PUT.toString(),
            PATCH.toString(),
            DELETE.toString()
    );
    private final List<String> ALLOWED_HEADERS      = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT_LANGUAGE,
            HttpHeaders.CONTENT_TYPE
    );
    private final List<String> EXPOSED_HEADERS      = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT_LANGUAGE
    );

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MessageSourceUtil messageSourceUtil)
            throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
                   .authorizeHttpRequests(auth -> auth
                           .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                           // User
                           .requestMatchers(GET, Endpoint.V1.User.GET_ONE).hasAuthority(Authority.READ_USER.getName())
                           .requestMatchers(GET, Endpoint.V1.User.GET_ACTIVE_LIST).hasAuthority(Authority.READ_ACTIVE_USERS.getName())
                           .requestMatchers(GET, Endpoint.V1.User.GET_DELETED_LIST).hasAuthority(Authority.READ_DELETED_USERS.getName())
                           .requestMatchers(POST, Endpoint.V1.User.ADD_ONE).hasAuthority(Authority.CREATE_USER.getName())
                           .requestMatchers(POST, Endpoint.V1.User.IMPORT_EXCEL).hasAuthority(Authority.IMPORT_EXCEL_USER.getName())
                           .requestMatchers(POST, Endpoint.V1.User.IMPORT_CSV).hasAuthority(Authority.IMPORT_CSV_USER.getName())
                           .requestMatchers(PUT, Endpoint.V1.User.UPDATE_INFO).hasAuthority(Authority.UPDATE_INFO.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.UPDATE_PASSWORD).hasAuthority(Authority.UPDATE_PASSWORD.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.RESET_PASSWORD_ONE).hasAuthority(Authority.RESET_PASSWORD.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.RESET_PASSWORD_LIST).hasAuthority(Authority.RESET_PASSWORD.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.SOFT_DELETE_ONE).hasAuthority(Authority.SOFT_DELETE_USER.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.SOFT_DELETE_LIST).hasAuthority(Authority.SOFT_DELETE_USER.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.RESTORE_ONE).hasAuthority(Authority.RESTORE_USER.getName())
                           .requestMatchers(PATCH, Endpoint.V1.User.RESTORE_LIST).hasAuthority(Authority.RESTORE_USER.getName())
                           .requestMatchers(DELETE, Endpoint.V1.User.HARD_DELETE_ONE).hasAuthority(Authority.HARD_DELETE_USER.getName())
                           .requestMatchers(DELETE, Endpoint.V1.User.HARD_DELETE_LIST).hasAuthority(Authority.HARD_DELETE_USER.getName())

                           // Product
                           .requestMatchers(GET, Endpoint.V1.Product.GET_DETAIL_ONE).hasAuthority(Authority.READ_PRODUCT.getName())
                           .requestMatchers(GET, Endpoint.V1.Product.GET_ACTIVE_LIST).hasAuthority(Authority.READ_ACTIVE_PRODUCTS.getName())
                           .requestMatchers(GET, Endpoint.V1.Product.GET_HIDDEN_LIST).hasAuthority(Authority.READ_HIDDEN_PRODUCTS.getName())
                           .requestMatchers(GET, Endpoint.V1.Product.GET_DELETED_LIST).hasAuthority(Authority.READ_DELETED_PRODUCTS.getName())
                           .requestMatchers(POST, Endpoint.V1.Product.ADD_ONE).hasAuthority(Authority.CREATE_PRODUCT.getName())
                           .requestMatchers(POST, Endpoint.V1.Product.IMPORT_EXCEL).hasAuthority(Authority.IMPORT_EXCEL_PRODUCT.getName())
                           .requestMatchers(POST, Endpoint.V1.Product.IMPORT_CSV).hasAuthority(Authority.IMPORT_CSV_PRODUCT.getName())
                           .requestMatchers(PUT, Endpoint.V1.Product.UPDATE_ONE).hasAuthority(Authority.UPDATE_PRODUCT.getName())
                           .requestMatchers(PUT, Endpoint.V1.Product.UPDATE_LIST_FROM_EXCEL).hasAuthority(Authority.UPDATE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.HIDE_ONE).hasAuthority(Authority.HIDE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.HIDE_LIST).hasAuthority(Authority.HIDE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.EXPOSE_ONE).hasAuthority(Authority.EXPOSE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.EXPOSE_LIST).hasAuthority(Authority.EXPOSE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.SOFT_DELETE_ONE).hasAuthority(Authority.SOFT_DELETE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.SOFT_DELETE_LIST).hasAuthority(Authority.SOFT_DELETE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.RESTORE_ONE).hasAuthority(Authority.RESTORE_PRODUCT.getName())
                           .requestMatchers(PATCH, Endpoint.V1.Product.RESTORE_LIST).hasAuthority(Authority.RESTORE_PRODUCT.getName())
                           .requestMatchers(DELETE, Endpoint.V1.Product.HARD_DELETE_ONE).hasAuthority(Authority.HARD_DELETE_PRODUCT.getName())
                           .requestMatchers(DELETE, Endpoint.V1.Product.HARD_DELETE_LIST).hasAuthority(Authority.HARD_DELETE_PRODUCT.getName())

                           // Address
                           .requestMatchers(POST, Endpoint.V1.Address.ADD_ONE).hasAuthority(Authority.CREATE_ADDRESS.getName())
                           .requestMatchers(PUT, Endpoint.V1.Address.UPDATE_ONE).hasAuthority(Authority.UPDATE_ADDRESS.getName())
                           .requestMatchers(DELETE, Endpoint.V1.Address.UPDATE_ONE).hasAuthority(Authority.DELETE_ADDRESS.getName())

                           // Brand
                           .requestMatchers(GET, Endpoint.V1.Brand.GET_ONE).hasAuthority(Authority.READ_BRAND.getName())
                           .requestMatchers(GET, Endpoint.V1.Brand.GET_ACTIVE_LIST).hasAuthority(Authority.READ_BRAND.getName())

                           // Cart Item
                           .requestMatchers(GET, Endpoint.V1.CartItem.GET_LIST).hasAuthority(Authority.READ_CART_ITEM.getName())
                           .requestMatchers(POST, Endpoint.V1.CartItem.ADD_ONE).hasAuthority(Authority.CREATE_CART_ITEM.getName())
                           .requestMatchers(PUT, Endpoint.V1.CartItem.UPDATE_ONE).hasAuthority(Authority.UPDATE_CART_ITEM.getName())
                           .requestMatchers(DELETE, Endpoint.V1.CartItem.DELETE).hasAuthority(Authority.DELETE_CART_ITEM.getName())

                           // Feedback
                           .requestMatchers(GET, Endpoint.V1.Feedback.GET_LIST_BY_PRODUCT).permitAll()
                           .requestMatchers(GET, Endpoint.V1.Feedback.GET_LIST_BY_USER).permitAll()
                           .requestMatchers(POST, Endpoint.V1.Feedback.ADD_ONE).hasAuthority(Authority.CREATE_FEEDBACK.getName())
                           .requestMatchers(PUT, Endpoint.V1.Feedback.UPDATE_ONE).hasAuthority(Authority.UPDATE_FEEDBACK.getName())

                           // Location
                           .requestMatchers(GET, Endpoint.V1.Location.GET_ALL_PROVINCES).permitAll()
                           .requestMatchers(GET, Endpoint.V1.Location.GET_ALL_DISTRICTS).permitAll()
                           .requestMatchers(GET, Endpoint.V1.Location.GET_ALL_WARDS).permitAll()

                           // Order
                           .requestMatchers(GET, Endpoint.V1.Order.GET_LIST_BY_USER_ID).hasAuthority(Authority.READ_ORDER.getName())
                           .requestMatchers(GET, Endpoint.V1.Order.GET_LIST_FOR_ADMIN).hasAuthority(Authority.READ_ORDER.getName())
                           .requestMatchers(GET, Endpoint.V1.Order.GET_DETAIL_FOR_USER).hasAuthority(Authority.READ_ORDER.getName())
                           .requestMatchers(GET, Endpoint.V1.Order.GET_DETAIL_FOR_ADMIN).hasAuthority(Authority.READ_ORDER.getName())

                           // Payment
                           .requestMatchers(POST, Endpoint.V1.Payment.CREATE).hasAuthority(Authority.CREATE_PAYMENT.getName())
                           .requestMatchers(GET, Endpoint.V1.Payment.CALLBACK).permitAll()

                           // Authentication
                           .requestMatchers(POST, Endpoint.V1.Auth.REGISTER).permitAll()
                           .requestMatchers(GET, Endpoint.V1.Auth.AUTHENTICATE).permitAll()

                           .anyRequest().authenticated()
                   )
                   .sessionManagement(
                           management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   )
                   .authenticationProvider(authenticationProvider)
                   .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                       httpSecurityExceptionHandlingConfigurer
                               .authenticationEntryPoint((request, response, authException) -> {
                                   response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                   response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                                   GlobalResponseDTO<NoPaginatedMeta, BlankData> responseBody =
                                           GlobalResponseDTO
                                                   .<NoPaginatedMeta, BlankData>builder()
                                                   .meta(NoPaginatedMeta
                                                                 .builder()
                                                                 .status(Status.ERROR)
                                                                 .message(messageSourceUtil.getMessage(ErrorMessage.Auth.AUTHENTICATE))
                                                                 .build())
                                                   .build();

                                   ObjectMapper objectMapper = new ObjectMapper();
                                   response.getWriter().write(objectMapper.writeValueAsString(responseBody));
                               })
                               .accessDeniedHandler((request, response, accessDeniedException) -> {
                                   response.setStatus(HttpStatus.FORBIDDEN.value());
                                   response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                                   GlobalResponseDTO<NoPaginatedMeta, BlankData> responseBody =
                                           GlobalResponseDTO
                                                   .<NoPaginatedMeta, BlankData>builder()
                                                   .meta(NoPaginatedMeta
                                                                 .builder()
                                                                 .status(Status.ERROR)
                                                                 .message(messageSourceUtil.getMessage(ErrorMessage.Auth.UNAUTHORIZED))
                                                                 .build()
                                                   )
                                                   .build();

                                   ObjectMapper objectMapper = new ObjectMapper();
                                   response.getWriter().write(objectMapper.writeValueAsString(responseBody));
                               });
                   })
                   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_HTTP_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setExposedHeaders(EXPOSED_HEADERS);
        configuration.setMaxAge((long) (24 * 60 * 60));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
