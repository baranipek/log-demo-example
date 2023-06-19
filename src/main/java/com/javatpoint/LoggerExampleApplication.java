package com.javatpoint;

import com.javatpoint.configuration.LoggerConfiguration;
import com.logger.enumeration.LogLevel;
import com.logger.impl.LightLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(LoggerConfiguration.class)

public class LoggerExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(LoggerExampleApplication.class, args);
  }



}