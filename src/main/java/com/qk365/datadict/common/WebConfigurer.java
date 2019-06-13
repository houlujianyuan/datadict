package com.qk365.datadict.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new Interceptor()).addPathPatterns("/api/**")
                    .excludePathPatterns("/main")
                    .excludePathPatterns("/")
                    .excludePathPatterns("/login")
                    .excludePathPatterns("/templates/**").excludePathPatterns("/static/**");
        }

}
