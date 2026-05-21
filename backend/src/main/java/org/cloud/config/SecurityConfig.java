package org.cloud.config;

import org.cloud.repository.verficiation.VerificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final VerificationRepository verificationRepository;

    SecurityConfig(VerificationRepository verificationRepository, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.verificationRepository = verificationRepository;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/api/v1/auth/**").permitAll()

            	    .requestMatchers(
            	        "/api/v1/recalls/search",
            	        "/api/v1/recalls/check/**",
            	        "/api/v1/recalls/detail/**"
            	    ).permitAll()
            	    .requestMatchers("/api/v1/ai/**").authenticated()
            	    .requestMatchers("/api/v1/users/me").authenticated()
            	    .requestMatchers("/api/v1/verifications/**").authenticated()
            	    .requestMatchers("/api/v1/recalls/**").authenticated()
            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            	    .anyRequest().authenticated()
            	)

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}

