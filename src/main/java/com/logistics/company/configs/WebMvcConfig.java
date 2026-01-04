package com.logistics.company.configs;

import com.logistics.company.interceptors.AuthInterceptor;
import com.logistics.company.interceptors.LoggedInInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final LoggedInInterceptor loggedInInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor, LoggedInInterceptor loggedInInterceptor) {
        this.authInterceptor = authInterceptor;
        this.loggedInInterceptor = loggedInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
        registry.addInterceptor(loggedInInterceptor).addPathPatterns("/api/auth/log-in");
    }
}
