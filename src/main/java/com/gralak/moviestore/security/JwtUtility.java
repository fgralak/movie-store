package com.gralak.moviestore.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
@Slf4j
public class JwtUtility
{
    private String secretKey;
    private Long expireAfterMinutes;

    public void createErrorInfo(Exception exception, HttpServletResponse response)
    {
        log.error("Error logging in: {}", exception.getMessage());
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        //response.sendError(FORBIDDEN.value());
    }

    public String createJWT(UserDetails user, HttpServletRequest request)
    {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expireAfterMinutes)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(HMAC256(secretKey.getBytes()));
    }

    public JWTVerifier getJWTVerifier()
    {
        JWTVerifier verifier;
        try
        {
            Algorithm algorithm = HMAC256(secretKey.getBytes());
            verifier = JWT.require(algorithm).build();
        } catch (JWTVerificationException exception)
        {
            throw new JWTVerificationException("TOKEN CANNOT BE VERIFIED");
        }
        return verifier;
    }

    public String getUsername(String token)
    {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private String[] getClaims(String token)
    {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim("roles").asArray(String.class);
    }

    public List<GrantedAuthority> getAuthorities(String token)
    {
        String[] claims = getClaims(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
