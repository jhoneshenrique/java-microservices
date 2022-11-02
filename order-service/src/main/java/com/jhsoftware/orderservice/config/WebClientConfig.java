package com.jhsoftware.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //Every time that an instance of webclient is created, it will be load balanced.
    @Bean
    @LoadBalanced //Client load balancing
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
