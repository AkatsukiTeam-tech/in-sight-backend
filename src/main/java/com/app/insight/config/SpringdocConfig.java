package com.app.insight.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringdocConfig {

    private static final String route = "com.app.insight.";

    @Bean
    public GroupedOpenApi frontApi() {
        return GroupedOpenApi.builder()
            .packagesToScan(route + "front")
            .group("springdoc-front")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .packagesToScan(route + "web.rest")
            .group("springdoc-admin")
            .pathsToMatch("/**")
            .build();
    }
}
