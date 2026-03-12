package com.medical.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)               // 允许携带 cookie / token
                .allowedOriginPatterns("*")           //  关键点
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .maxAge(3600);
    }
}
