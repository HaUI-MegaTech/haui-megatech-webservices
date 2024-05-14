package shop.haui_megatech.configuration.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.haui_megatech.utility.JwtTokenUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String             AUTH_PREFIX = "Bearer ";
    private final        UserDetailsService userDetailsService;
    private final        JwtTokenUtil       jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith(AUTH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(AUTH_PREFIX.length());
        username = jwtUtil.extractUsername(jwt);
        if (username != null
            && SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.isValidToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
