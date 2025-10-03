package com.example.usuariosprimacapi.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import jakarta.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
public class TimezoneConfig {

    @PostConstruct
    public void init() {
        // Set default timezone to UTC for the entire application
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .modules(new JavaTimeModule())
                .timeZone(TimeZone.getTimeZone(ZoneOffset.UTC))
                .simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
}