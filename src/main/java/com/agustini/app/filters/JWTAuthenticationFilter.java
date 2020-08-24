package com.agustini.app.filters;

import com.agustini.app.interceptors.ThreadTenantStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.agustini.app.config.Constants;
import com.agustini.app.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;

    public  JWTAuthenticationFilter(AuthenticationManager authManager) {

        this.authManager = authManager;
        super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //TODO: VER ESTO
        Usuario credentials = null;

        //Obtenemos el tenant por URI y asignamos al Thread
        String tenant = request.getRequestURI().split("/")[1];
        ThreadTenantStorage.setTenantId(tenant);

        System.out.println(ThreadTenantStorage.getTenantId());
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        //Usuario user = (Usuario) auth.getPrincipal();
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();
        //response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
        response.getWriter().write("{\"" + Constants.HEADER_AUTHORIZACION_KEY + "\":\"" + Constants.TOKEN_BEARER_PREFIX + " " + token + "\"}");

    }
}
