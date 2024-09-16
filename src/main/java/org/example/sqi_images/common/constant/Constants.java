package org.example.sqi_images.common.constant;

import java.util.List;

public class Constants {

    // Employee
    public static final String SQISOFT_EMAIL = "@sqisoft.com";

    // Token
    public static final String TOKEN_COOKIE_NAME = "AccessToken";

    // Auth
    public static final String ADD_AUTH_API_PATH = "/api/auth/logout";
    public static final String ADD_PROFILE_API_PATH = "/api/profiles/**";
    public static final List<String> EXCLUDE_AUTH_API_PATH = List.of("/api/auth/login", "/api/auth/sign-up");
}
