package com.pedrocatelan.form;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica as regras a todos os endpoints da sua API
                .allowedOrigins("http://localhost:3000") // 👈 O FRONTEND (Next.js)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos (headers)
                .allowCredentials(true); // Permite credenciais (cookies, tokens de autenticação)
    }
}
