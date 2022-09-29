package com.gamekeys.gameshop.constant;

public class SecurityConstant {


    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds

    public static final String TOKEN_PREFIX = "Bearer "; // Whoever gives me this token, I don't have to do any further verification

    public static final String JWT_TOKEN_HEADER = "Jwt-Token"; //

    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    public static final String GAME_KEY_INC = "GameKey, INC";

    public static final String GAME_KEY_ADMINISTRATION = "User Management Portal";

    public static final String ROLES = "roles";

    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";

    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";

    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    //public static final String[] PUBLIC_URLS = { "api/main", "api/user/login", "api/user/register", "api/user/image/**" };

    public static final String[] PUBLIC_URLS = { "**" };
}
