package com.ceramicthree.deadInsideSN.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/static/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry
                .addResourceHandler("/static/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");

    }
}

