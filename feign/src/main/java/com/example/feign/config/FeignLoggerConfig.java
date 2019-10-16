package com.example.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLoggerConfig {
    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }
}
