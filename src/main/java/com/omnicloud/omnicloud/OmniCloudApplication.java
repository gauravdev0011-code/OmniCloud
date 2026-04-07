package com.omnicloud.omnicloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.omnicloud")
public class OmniCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(OmniCloudApplication.class, args);
    }
}