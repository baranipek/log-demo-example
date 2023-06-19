package com.demo;

import com.demo.configuration.LoggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LoggerConfiguration.class)

public class LoggerExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(LoggerExampleApplication.class, args);
  }



}