package dsm.pick2024.global.security.jwt.path

object SecurityPaths {
    val PERMITALLPATHS = listOf(
        "/admin/login",
        "/admin/refresh",
        "/user/login",
        "/user/refresh",
        "/main",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/dsm-pick/swagger-ui/index.html",
        "/dsm-pick/swagger-ui/index.html/**",
        "/user/signup",
        "/mail/send",
        "/mail/check",
        "/admin/signup",
        "/PiCK_Logo.png",
        "/user/password",
        "/admin/key"
    )
}
