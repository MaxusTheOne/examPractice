package dat3.grocery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_ORIGINS = new String[]{
            "http://localhost:5173",
            "http://localhost:5174",
            "http://localhost:3000",
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(crsf -> crsf.disable())
                .authorizeRequests()
                .anyRequest().permitAll();
        return http.build();
    }




            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALLOWED_ORIGINS)
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }


}
