package ru.opgmap.opgmap_comment_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(authorize ->
                        // swagger
                        authorize.antMatchers("/v3/api-docs/**", "/swagger-ui/**",
                                        "/swagger-ui.html", "/webjars/swagger-ui/**",
                                        "/api/loggedin/confirm/**", "/api/loggedin/confirm/",
                                        "/public/oauth2-redirect.html", "/context-path/v3/api-docs",
                                        "/context-path/v3/api-docs/**", "/swagger-ui-custom.html",
                                        "/oauth2-redirect.html").permitAll()
                )
                .oauth2ResourceServer(server ->
                        server.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()));

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new SecurityRealmRoleConverter());
        return jwtConverter;
    }

}