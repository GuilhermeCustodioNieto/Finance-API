package com.github.guilhermecustodionieto.finance_api.config;

import com.github.guilhermecustodionieto.finance_api.entities.User;
import com.github.guilhermecustodionieto.finance_api.entities.UserDetailsImpl;
import com.github.guilhermecustodionieto.finance_api.services.JwtTokenService;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] ENDPOINTS_WITHOUT_AUTH = {
            "/auth/login",
            "/auth/register",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isPublicEndpoint(request)) {

            filterChain.doFilter(request, response);
            return;
        }
        String token = recoverToken(request);

        if (token != null && !token.isBlank()) {
            String subject = jwtTokenService.getSubjectFromToken(token);
            User user = userService.findByEmail(subject);
            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new BadCredentialsException("Token ausente");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        return null;
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String uri = request.getRequestURI();

        for (String endpoint : ENDPOINTS_WITHOUT_AUTH) {
            if (endpoint.endsWith("/**")) {
                String prefix = endpoint.substring(0, endpoint.length() - 3);
                if (uri.startsWith(prefix)) {
                    return true; // É público
                }
            } else {
                if (uri.equals(endpoint)) {
                    return true; // É público
                }
            }
        }

        return false; // Não é público, precisa autenticar
    }
}
