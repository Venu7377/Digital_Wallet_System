package com.project.DigitalWallet;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertyReader {

    private final Environment environment;

    public PropertyReader(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}
