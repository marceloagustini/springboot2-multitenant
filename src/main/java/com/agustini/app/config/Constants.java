package com.agustini.app.config;

public class Constants {
    public static final String LOGIN_URL = "/{tenantid}/auth/login";
    public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 2; //2 Dias
    public static final String SECRET_KEY = "{id}encodedPassword";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer";
}
