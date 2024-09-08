package com.hunmuk.api;

import com.hunmuk.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class HunmukApplication {

    public static void main(String[] args) {
        SpringApplication.run(HunmukApplication.class, args);
    }

}
