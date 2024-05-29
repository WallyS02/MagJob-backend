package com.keepitup.magjobbackend.configuration;

import com.keepitup.magjobbackend.user.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final AntPathRequestMatcher[] permitAllList = {
            new AntPathRequestMatcher("/api/users", "POST"),
            new AntPathRequestMatcher("/api/users/login"),
            new AntPathRequestMatcher("/v3/api-docs/**", "GET"),
            new AntPathRequestMatcher("/swagger-ui/**")
    };

    private static final AntPathRequestMatcher[] authenticatedList = {
            new AntPathRequestMatcher("/api/users/{id}"),
            new AntPathRequestMatcher("/api/users", "GET"),
//            new AntPathRequestMatcher("/v3/api-docs/**", "GET"),
//            new AntPathRequestMatcher("/swagger-ui/**"),
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
            new AntPathRequestMatcher("/api/invitations/{userId}/{organizationId}"),
            new AntPathRequestMatcher("/api/tasks/**"),
            new AntPathRequestMatcher("/api/assignees/**"),
            new AntPathRequestMatcher("/api/announcements"),
            new AntPathRequestMatcher("/api/announcements/{id}"),
            new AntPathRequestMatcher("/api/organizations/{organizationId}/announcements"),
            new AntPathRequestMatcher("/api/announcement-receivers"),
            new AntPathRequestMatcher("/api/announcement-receivers/{id}"),
            new AntPathRequestMatcher("/api/announcements/{announcementId}/announcement-receivers"),
            new AntPathRequestMatcher("/api/members/{memberId}/announcement-receivers"),
            new AntPathRequestMatcher("/api/materials"),
            new AntPathRequestMatcher("/api/materials/{id}"),
            new AntPathRequestMatcher("/api/organizations/{organizationId}/materials"),
            new AntPathRequestMatcher("/api/material-receivers"),
            new AntPathRequestMatcher("/api/material-receivers/{id}"),
            new AntPathRequestMatcher("/api/materials/{materialId}/material-receivers"),
            new AntPathRequestMatcher("/api/members/{memberId}/material-receivers"),
            new AntPathRequestMatcher("/api/roles"),
            new AntPathRequestMatcher("/api/roles/{id}"),
            new AntPathRequestMatcher("/api/organizations/{organizationId}/roles"),
            new AntPathRequestMatcher("/api/role-members"),
            new AntPathRequestMatcher("/api/role-members/{id}"),
            new AntPathRequestMatcher("/api/roles/{roleId}/role-members"),
            new AntPathRequestMatcher("/api/members/{memberId}/role-members")
    };

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(permitAllList).permitAll()
                                .requestMatchers(authenticatedList).authenticated()
                                .anyRequest().denyAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
