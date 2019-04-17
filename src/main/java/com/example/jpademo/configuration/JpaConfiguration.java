package com.example.jpademo.configuration;

import com.example.jpademo.Interceptor.loginInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.jpademo.repository")
@EntityScan("com.example.jpademo.domain")
public class JpaConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/register");
        //registry.addInterceptor(new loginInterceptor()).addPathPatterns("/register");
        super.addInterceptors(registry);
    }
}
