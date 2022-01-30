package com.g4bor.payment.acquirer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions()
                .sameOrigin();

        // Session management is not needed
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Disable login/logout page & CSRF
        http.formLogin().disable()
            .logout().disable()
            .csrf().disable();

        // Use API-Key authentication
        http.authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**", "/api-doc/**");
    }
}
