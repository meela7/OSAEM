package com.naemp.osaem;

import org.ajar.swaggermvcui.SwaggerSpringMvcUi;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class SpringConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * Class Name: SpringConfig.java 
	 * Description:
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.30
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS)
                .addResourceLocations(SwaggerSpringMvcUi.WEB_JAR_RESOURCE_LOCATION).setCachePeriod(0);
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_PREFIX);
        resolver.setSuffix(SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_SUFFIX);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
}