package com.versed.rl_trainer_svc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import com.versed.rl_trainer_svc.security.JwtFilter;
import com.versed.rl_trainer_svc.security.OAuth2LoginSuccessHandler;

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
                .exceptionHandling(ex -> ex.defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    PathPatternRequestMatcher.withDefaults().matcher("/api/**")))
                .securityContext(sc -> sc.securityContextRepository(new RequestAttributeSecurityContextRepository()))
                .build();
    }
    
    @Bean 
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter){
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>(jwtFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
}
