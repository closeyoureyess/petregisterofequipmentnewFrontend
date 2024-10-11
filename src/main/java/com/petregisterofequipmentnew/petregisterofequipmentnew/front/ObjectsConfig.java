package com.petregisterofequipmentnew.petregisterofequipmentnew.front;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ObjectsConfig {

   /* @Value("${api.base.url}")
    private String rootUri;

    @Bean
    public RestClient builderObject(){
        return RestClient.builder()
                .baseUrl(rootUri)
                .build();
    }*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8080/")
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

}
