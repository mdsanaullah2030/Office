package com.itshop.ecommerce.configaration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${image.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir + "/location/")
        .addResourceLocations("file:" + uploadDir + "/pcBuilder/")
        .addResourceLocations("file:" + uploadDir + "/pcforpartadd/")
                .addResourceLocations("file:" + uploadDir + "/homeimage/")
                .addResourceLocations("file:" + uploadDir + "/media/")
                .addResourceLocations("file:" + uploadDir + "/ccbuilderitem/")
                .addResourceLocations("file:" + uploadDir + "/desktop/")
                .addResourceLocations("file:" + uploadDir + "/allLaptop/")
                .addResourceLocations("file:" + uploadDir + "/allPrinter/")
                .addResourceLocations("file:" + uploadDir + "/allCamera/")
                .addResourceLocations("file:" + uploadDir + "/allnetwork/")


        ;
    }



}
