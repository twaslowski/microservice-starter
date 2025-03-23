package com.twaslowski.linkshortener.config;

import static org.springframework.security.config.http.SessionCreationPolicy.NEVER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

  @Bean
  public SecurityFilterChain configureWebSecurity(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(this::configureRestAuthorizations)
        .sessionManagement(session -> session.sessionCreationPolicy(NEVER))
        .build();
  }

  private void configureRestAuthorizations(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationRegistry) {
    authorizationRegistry
        .requestMatchers("/*").permitAll()
        .requestMatchers("/api/v1/*").permitAll()
        .requestMatchers("/api/v1/user/*").permitAll()
        .requestMatchers("/actuator/health").permitAll()
        .anyRequest().denyAll();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
