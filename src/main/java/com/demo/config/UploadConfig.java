package com.demo.config;


import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class UploadConfig {
    @Bean
    public MultipartConfigElement getMultipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("10240KB"));  //单个文件最大
        factory.setMaxRequestSize(DataSize.parse("102400KB")); // 设置总上传数据总大小
        return factory.createMultipartConfig();
    }
}

