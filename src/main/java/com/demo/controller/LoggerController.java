package com.demo.controller;

import com.logger.enumeration.LogLevel;
import com.logger.impl.LightLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

  private final LightLogger logger;

  @Autowired
  public LoggerController(LightLogger logger) {
    this.logger = logger;
  }

  @RequestMapping("/")
  public String hello() {
    logger.log(LogLevel.WARNING, "hello warning");
    logger.log(LogLevel.DEBUG, "hello debug");
    logger.log(LogLevel.INFO, "hello info");
    logger.log(LogLevel.ERROR, "hello error");
    return "Hello";
  }
}
