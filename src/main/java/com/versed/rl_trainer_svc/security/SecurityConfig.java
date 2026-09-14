package com.versed.rl_trainer_svc.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            OAuth2LoginSuccessHandler loginHandler, JwtFilter jwtFilter) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.requestMatchers("/error")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> oauth.successHandler(loginHandler))
                .build();
    }
    
    @Bean 
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter){
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>(jwtFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
}
