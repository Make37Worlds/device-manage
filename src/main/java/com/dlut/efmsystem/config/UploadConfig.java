package com.dlut.efmsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan
@Configuration
public class UploadConfig implements WebMvcConfigurer {

    @Value("${file.uploadPath}")
    String uploadPath;  //项目地址
    @Value("${file.accessPath}")
    String accessPath;   //虚拟地址

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(accessPath+"**").addResourceLocations("file:"+uploadPath);
    }

}
