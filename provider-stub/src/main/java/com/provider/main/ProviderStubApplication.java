package com.provider.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
class ProviderStubApplication{
    public static void main(String[] args) {
        log.info("Provider stub starting..");
        SpringApplication.run(ProviderStubApplication.class ,  args);
        log.info("Provider stub started...");
    }
}