package com.hunmuk.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "hunmuk")
public class AppConfig {

    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey.getBytes();
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }
}
