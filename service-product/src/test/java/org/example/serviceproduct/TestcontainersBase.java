//package org.example.serviceproduct;
//
//
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestcontainersBase.TestSecurityConfiguration.class)
//@AutoConfigureWebTestClient
//@ActiveProfiles("test")
//@TestConfiguration(proxyBeanMethods = false)
//@Testcontainers
//abstract class TestcontainersBase {
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.2")
//            .withDatabaseName("products")
//            .withUsername("username")
//            .withPassword("password")
//            .withExposedPorts(5432);
//
//    @DynamicPropertySource
//    static void postgres(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.datasource.username", postgres::getUsername);
//    }
//
//    @TestConfiguration
//    static class TestSecurityConfiguration {
//
//        @Bean
//        SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
//            return http.authorizeHttpRequests(registry -> registry
//                            .anyRequest()
//                            .permitAll())
//                    .build();
//
//        }
//    }
//}

