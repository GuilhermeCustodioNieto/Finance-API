package com.github.guilhermecustodionieto.finance_api.config;

import com.github.guilhermecustodionieto.finance_api.entities.User;
import com.github.guilhermecustodionieto.finance_api.entities.UserDetailsImpl;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.AuthenticationException;
import com.github.guilhermecustodionieto.finance_api.services.JwtTokenService;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.persister.collection.OneToManyPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
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
        }

        filterChain.doFilter(request, response);
    }


    private String recoveryToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer", "").trim();
        }

        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
