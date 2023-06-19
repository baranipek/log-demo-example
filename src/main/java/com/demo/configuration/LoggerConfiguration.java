package com.demo.configuration;

import com.logger.enumeration.LogLevel;
import com.logger.impl.ConsoleLogTarget;
import com.logger.impl.EmailLogTarget;
import com.logger.impl.LightLogger;
import com.logger.LogTarget;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "mylogger")
public class LoggerConfiguration {

  private LogLevel minLogLevel;
  private List<LogTargetConfig> logTargets;

  public void setMinLogLevel(LogLevel minLogLevel) {
    this.minLogLevel = minLogLevel;
  }

  public void setLogTargets(List<LogTargetConfig> logTargets) {
    this.logTargets = logTargets;
  }

  @Bean
  public LightLogger lightLogger() {
    LightLogger lightLogger = new LightLogger(minLogLevel);

    for (LogTargetConfig targetConfig : logTargets) {
      LogTarget logTarget;
      if (targetConfig.getType().equalsIgnoreCase("console")) {
        logTarget = new ConsoleLogTarget();
      } else if (targetConfig.getType().equalsIgnoreCase("email")) {
        logTarget = new EmailLogTarget();
      } else {
        continue;
      }
      lightLogger.addLogTarget(logTarget);

      if (targetConfig.getLevel() != null) {
        lightLogger.setLogLevelForTarget(logTarget, targetConfig.getLevel());
      }
    }

    return lightLogger;
  }

  private static class LogTargetConfig {
    private String type;
    private LogLevel level;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public LogLevel getLevel() {
      return level;
    }

    public void setLevel(LogLevel level) {
      this.level = level;
    }
  }
}
