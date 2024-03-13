package com.keepitup.magjobbackend.configuration;


import com.keepitup.magjobbackend.jwt.CustomJwt;
import com.keepitup.magjobbackend.jwt.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final AntPathRequestMatcher[] permitAllList = {
            /*new AntPathRequestMatcher("/api/users", "POST"),
            new AntPathRequestMatcher("/api/users/login")*/
            new AntPathRequestMatcher("/v3/api-docs/**", "GET"),
            new AntPathRequestMatcher("/swagger-ui/**"),
    };

    private static final AntPathRequestMatcher[] authenticatedList = {
            new AntPathRequestMatcher("/api/users/{id}"),
            new AntPathRequestMatcher("/api/users", "GET"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/api/organizations"),
            new AntPathRequestMatcher("/api/organizations/{id}"),
            new AntPathRequestMatcher("/api/members"),
            new AntPathRequestMatcher("/api/members/{id}"),
            new AntPathRequestMatcher("/api/organizations/{organizationId}/members"),
            new AntPathRequestMatcher("/api/organizations/users/{userId}"),
            new AntPathRequestMatcher("/healthcheck/**"),
            new AntPathRequestMatcher("/api/invitations"),
            new AntPathRequestMatcher("/api/invitations/{id}"),
            new AntPathRequestMatcher("/api/organizations/{organizationId}/invitations"),
            new AntPathRequestMatcher("/api/users/{userId}/invitations"),
            new AntPathRequestMatcher("/api/invitations/{userId}/{organizationId}")
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Rest of old implementation
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(permitAllList).permitAll()
                        .requestMatchers(authenticatedList).authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(customJwtConverter())
                ));
        return http.build();
    }
    @Bean
    public Converter<Jwt, CustomJwt> customJwtConverter() {
        return new CustomJwtConverter();
    }
}