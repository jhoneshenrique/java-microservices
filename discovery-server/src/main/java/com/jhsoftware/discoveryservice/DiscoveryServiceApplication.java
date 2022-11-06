package com.jhsoftware.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaServer //Transform it into an Eureka server
public class DiscoveryServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(DiscoveryServiceApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}
