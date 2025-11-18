package com.zexplore.travelplanner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.zexplore.travelplanner.model.enums.Difficulty;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Difficulty.class, source -> Difficulty.valueOf(source.toUpperCase()));
    }
}

