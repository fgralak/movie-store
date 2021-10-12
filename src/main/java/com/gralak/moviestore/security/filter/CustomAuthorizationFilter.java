package com.gralak.moviestore.security.filter;


import com.gralak.moviestore.security.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter
{
    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        if (request.getServletPath().equals("/api/login"))
        {
            filterChain.doFilter(request, response);
        } else
        {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            {
                try
                {
                    String token = authorizationHeader.substring("Bearer ".length());
                    String username = jwtUtility.getUsername(token);
                    List<GrantedAuthority> authorities = jwtUtility.getAuthorities(token);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (Exception e)
                {
                    jwtUtility.createErrorInfo(e, response);
                    //filterChain.doFilter(request, response);
                }

            } else
            {
                filterChain.doFilter(request, response);
            }
        }
    }
}
