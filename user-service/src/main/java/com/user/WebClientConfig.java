package com.user;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient configureWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://localhost:8001/movies").build();
    }


}
