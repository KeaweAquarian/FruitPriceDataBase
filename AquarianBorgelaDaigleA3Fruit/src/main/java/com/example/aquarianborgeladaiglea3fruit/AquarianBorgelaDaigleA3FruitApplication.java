package com.example.aquarianborgeladaiglea3fruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@EnableEurekaClient is deprecated no need to annotate at springboot main as we are using
// spring-cloud-starter-netflix-eureka-client dependency in pom
@SpringBootApplication
public class AquarianBorgelaDaigleA3FruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquarianBorgelaDaigleA3FruitApplication.class, args);
    }

}
