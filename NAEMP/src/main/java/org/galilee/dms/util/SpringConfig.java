package org.galilee.dms.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.ajar.swaggermvcui.SwaggerSpringMvcUi;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringConfig extends WebMvcConfigurerAdapter {

	
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
