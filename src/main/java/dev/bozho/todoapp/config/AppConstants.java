package dev.bozho.todoapp.config;

public class AppConstants {

    public static final String[] PUBLIC_URLS = {
        "/api/v1/auth",
        "/api/v1/auth/**",
        "/api/v1/user",
        "/api/v1/user/**",
        "/api/v1/tasks",
        "/api/v1/tasks/**"
    };
}
