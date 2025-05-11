package org.example.spring10homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Spring10HomeWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring10HomeWorkApplication.class, args);
    }

}
