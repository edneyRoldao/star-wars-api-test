package com.test.starwarsapi.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients({"com.test.starwarsapi.client"})
public class AppConfig {
}
