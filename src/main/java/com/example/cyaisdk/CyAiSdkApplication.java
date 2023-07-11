package com.example.cyaisdk;

import com.example.cyaisdk.client.AiClient;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("cy.ai")
@Configuration
@Data
@ComponentScan
public class CyAiSdkApplication {

    private String accessKey;

    private String secretKey;


    @Bean
    public AiClient aiClient() {
        return new AiClient(accessKey,secretKey);
    }

}
