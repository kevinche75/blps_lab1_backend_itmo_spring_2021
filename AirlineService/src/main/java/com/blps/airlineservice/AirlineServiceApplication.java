package com.blps.airlineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AirlineServiceApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(AirlineServiceApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(applicationClass);
    }

    private static final Class<AirlineServiceApplication> applicationClass = AirlineServiceApplication.class;
}
