/*
package com.ib.ibcheckweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
@Configuration
public class IBMvcConfig extends WebMvcConfigurerAdapter{
 
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
 
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
 
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
 
                registry.addViewController("/").setViewName("getbyAccount");
                registry.addViewController("/getbyAccount.html").setViewName("getbyAccount");
            }
        };
 
        return adapter;
    }
}
*/