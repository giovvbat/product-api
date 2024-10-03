package com.giovanna.demo.infra.security;

import com.giovanna.demo.model.UserModel;
import com.giovanna.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;
    private final ArrayList<String> freeEndpoints = new ArrayList<>();

    @PostConstruct
    private void init() {
        freeEndpoints.add("/users/register");
        freeEndpoints.add("/users/login");
        freeEndpoints.add("/roles/first-admin");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        /*freeing endpoints with zero authentication needed*/
        if (isFilterSkippable(request, freeEndpoints)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.resolveToken(request);

        /*success while retrieving token*/
        if (token != null) {
            String login = tokenService.verifyToken(token);

            /*success while verifying user from token*/
            if (login != null) {
                Optional<UserModel> user = userRepository.findByUsername(login);

                if (user.isEmpty()) {
                    /*fail while verifying token, no authentication possible*/
                    filterChain.doFilter(request, response);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }

        /*no token provided*/
        return null;
    }

    public boolean isFilterSkippable(HttpServletRequest request, ArrayList<String> freeEndpoints) {
        for (String freeEndpoint : freeEndpoints) {
            if (request.getRequestURI().startsWith(freeEndpoint) && request.getRequestURI().endsWith(freeEndpoint)) {
                return true;
            }
        }
        return false;
    }
}