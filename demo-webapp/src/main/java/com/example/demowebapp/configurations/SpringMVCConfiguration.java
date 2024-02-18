package com.example.demowebapp.configurations;

import com.example.demowebapp.entities.Month;
import com.example.demowebapp.interceptors.APILoggingInterceptor;
import com.example.demowebapp.interceptors.MonthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SpringMVCConfiguration implements WebMvcConfigurer {


    @Autowired
    private APILoggingInterceptor apiLoggingInterceptor;

    @Autowired
    private MonthInterceptor monthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLoggingInterceptor);
        registry.addInterceptor(monthInterceptor);
    }
}
