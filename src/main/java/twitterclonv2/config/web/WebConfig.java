package twitterclonv2.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("uploads/profileImages/**")
                .addResourceLocations("file:uploads/profileImages/");

        registry.addResourceHandler("uploads/backgroundImages/**")
                .addResourceLocations("file:uploads/backgroundImages/");
    }
}
