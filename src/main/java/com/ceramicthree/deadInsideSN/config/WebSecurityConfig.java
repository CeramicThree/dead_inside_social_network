package com.ceramicthree.deadInsideSN.config;

import com.ceramicthree.deadInsideSN.services.OidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OidcUserService oidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth", "/static/js/**", "/static/css/**", "/static/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().loginPage("/auth")
                .userInfoEndpoint()
                .oidcUserService(oidcUserService);
    }

}
