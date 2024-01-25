package rsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Permit all requests to your WebSocket endpoint
                .requestMatchers("/rsocket").permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
            .and()
            // Additional configurations can be added here
            ;

        return http.build();
    }
}