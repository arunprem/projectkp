package com.keralapolice.projectk.config.security;

public class SecurityConstants {
    public static final String  SIGN_UP_URLS="/api/user/**";
    public static final String SECRET_KEY="SecrectKeyToGenerateJwtToken";
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final long EXPIRATION_TIME=30_000;//30 seconds

}
