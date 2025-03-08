package com.example.novel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置 /uploads/ 路径为静态资源目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/");
    }
}