package org.dev9.topaz.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String RESOURCE_PATH="/resource";
    public static final String ENV_RESOURCE_PATH="/topaz";
    public static final String[] IMAGE_EXT_LIST=new String[]{"jpg", "png", "jpeg", "bmp"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler(RESOURCE_PATH+"/image/**")
                .addResourceLocations(ENV_RESOURCE_PATH+"/image/");
    }
}
