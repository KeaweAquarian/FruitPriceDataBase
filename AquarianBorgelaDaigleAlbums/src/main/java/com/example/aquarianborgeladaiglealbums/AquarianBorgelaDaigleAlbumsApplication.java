package com.example.aquarianborgeladaiglealbums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEurekaClient is deprecated no need to annotate at springboot main as we are using
// spring-cloud-starter-netflix-eureka-client dependency in pom
@SpringBootApplication
public class AquarianBorgelaDaigleAlbumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquarianBorgelaDaigleAlbumsApplication.class, args);
    }

}
