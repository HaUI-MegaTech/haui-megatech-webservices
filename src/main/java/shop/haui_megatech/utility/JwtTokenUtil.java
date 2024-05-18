package shop.haui_megatech.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.exception.AppException;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {
    private final MessageSourceUtil messageSourceUtil;
    @Value("${app.jwt.secret-key}")
    private       String            SECRET_KEY;

    public JwtTokenUtil(MessageSourceUtil messageSourceUtil) {this.messageSourceUtil = messageSourceUtil;}

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = this.extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            log.error(messageSourceUtil.getMessage(ErrorMessage.Auth.EXPIRED_TOKEN));
        } catch (SignatureException e) {
            log.error(messageSourceUtil.getMessage(ErrorMessage.Auth.MALFORMED));
        }
        return null;
    }

    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>() {{
            put("id", userDetails.getUsername());
        }}, userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                   .setId("Hello this id token id")
                   .setClaims(extraClaims)
                   .setSubject(userDetails.getUsername())
                   .setIssuedAt(new Date(Instant.now().toEpochMilli()))
                   .setExpiration(new Date(
                                   Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                           )
                   )
                   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        if (this.isTokenExpired(token))
            throw new ExpiredJwtException(null, null, ErrorMessage.Auth.EXPIRED_TOKEN);
        final String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException, SignatureException {

        return Jwts.parserBuilder()
                   .setSigningKey(this.getSignInKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
