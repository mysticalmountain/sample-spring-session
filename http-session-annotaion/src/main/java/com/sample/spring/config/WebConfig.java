package com.sample.spring.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by andongxu on 16-3-29.
 */
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        registry.addRedirectViewController("/", "logout");
        registry.addViewController("/").setViewName("login");
    }

}
