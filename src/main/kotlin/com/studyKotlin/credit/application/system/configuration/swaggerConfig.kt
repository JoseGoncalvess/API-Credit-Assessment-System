package com.studyKotlin.credit.application.system.configuration

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class swaggerConfig {
    @Bean
    fun publicApi(): GroupedOpenApi? {
        return  GroupedOpenApi.builder().group("springcreditapplicationsystem")
            .pathsToMatch("/api/credits/**", "/api/customers/**")
            .build()
    }

}