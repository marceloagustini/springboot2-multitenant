package com.agustini.app.filters;

import com.agustini.app.config.Constants;
import com.agustini.app.interceptors.ThreadTenantStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String tenant = request.getRequestURI().split("/")[1];
        ThreadTenantStorage.setTenantId(tenant);

        // Se procesa el token y se recupera el usuario.
        Claims token = Jwts.parser()
                .setSigningKey(Constants.SECRET_KEY)
                .parseClaimsJws(header.replace(Constants.TOKEN_BEARER_PREFIX, "").trim())
                .getBody();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                token.getSubject(),
                null,
                Collections.emptyList()
        ));

        chain.doFilter(request, response);
    }
}