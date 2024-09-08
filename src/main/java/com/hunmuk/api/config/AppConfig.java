package com.hunmuk.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "hunmuk")
public class AppConfig {

    Hello hello;

    @Data
    public static class Hello{
        private String name;
        private String age;
        private String job;

    }
}
