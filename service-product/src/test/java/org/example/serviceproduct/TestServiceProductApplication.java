package org.example.serviceproduct;

import org.springframework.boot.SpringApplication;

public class TestServiceProductApplication {

    public static void main(String[] args) {
        SpringApplication.from(ServiceProductApplication::main).with(TestServiceProductApplication.class).run(args);
    }

}
