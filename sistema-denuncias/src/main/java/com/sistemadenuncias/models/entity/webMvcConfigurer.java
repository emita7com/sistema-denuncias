package com.sistemadenuncias.models.entity;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public interface webMvcConfigurer {

    void addResourceHandlers(ResourceHandlerRegistry registry);

}
