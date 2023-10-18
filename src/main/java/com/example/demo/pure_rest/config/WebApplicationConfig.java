package com.example.demo.pure_rest.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class WebApplicationConfig {

    @Bean
    public Filter shallowEtagHeaderFilter(ServletContext servletContext) {
        var etagHeaderFilter = new ShallowEtagHeaderFilter();
        servletContext.addFilter("etagHeaderFilter", etagHeaderFilter);
        return etagHeaderFilter;
    }
}
