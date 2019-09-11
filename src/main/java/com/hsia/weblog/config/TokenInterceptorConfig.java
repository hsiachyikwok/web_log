package com.hsia.weblog.config;

import com.hsia.weblog.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author: hsia
 * @Date: 2018/1/25 上午10:47
 * @Description:
 */
@Configuration
public class TokenInterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(tokenInterceptor());
        ir.addPathPatterns("/admin/**");
        ir.excludePathPatterns("/admin/login");
        ir.excludePathPatterns("/admin/logout");
        ir.addPathPatterns("/api/**");
    }
}
