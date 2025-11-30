package com.business.springservice.config;

import com.business.springservice.security.JwtAuthenticationFilter;
import com.business.springservice.security.RoleAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final RoleAuthorizationFilter roleAuthorizationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthenticationFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RoleAuthorizationFilter> roleFilter() {
        FilterRegistrationBean<RoleAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(roleAuthorizationFilter);
        registrationBean.addUrlPatterns("/users", "/users/*", "/admin/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
