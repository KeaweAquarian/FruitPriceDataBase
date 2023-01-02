package com.example.aquarianborgeladaiglea3totalprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableEurekaClient is deprecated no need to annotate at springboot main as we are using
// spring-cloud-starter-netflix-eureka-client dependency in pom
@SpringBootApplication
public class AquarianBorgelaDaigleA3TotalPriceApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AquarianBorgelaDaigleA3TotalPriceApplication.class, args);
    }

}
