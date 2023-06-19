package com.demo.controller;

import com.logger.impl.EmailLogTarget;
import com.logger.impl.LightLogger;
import com.logger.enumeration.LogLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class LoggerControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private LightLogger lightLogger;

  private TestRestTemplate restTemplate = new TestRestTemplate(
      TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

  private ByteArrayOutputStream consoleOutput;
  private PrintStream originalConsole;

  @BeforeEach
  public void setUpConsoleOutput() {
    // Redirect console output to a ByteArrayOutputStream
    consoleOutput = new ByteArrayOutputStream();
    originalConsole = System.out;
    System.setOut(new PrintStream(consoleOutput));
  }

  @AfterEach
  public void restoreConsoleOutput() {
    // Restore the original console output
    System.setOut(originalConsole);
  }

  @Test
  public void givenWarningAsMinLevelWhenLogAllLevelsThenLogOnlyWarningAndError() {

    lightLogger.setMinLogLevel(LogLevel.WARNING);

    restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    String consoleLogs = consoleOutput.toString();
    assertThat(consoleLogs).contains("[Console] WARNING: hello warning");
    assertThat(consoleLogs).contains("[Console] ERROR: hello error");
    assertThat(consoleLogs).doesNotContain("[Console] INFO: hello error");
    assertThat(consoleLogs).doesNotContain("[Console] DEBUG: hello debug");

  }


  @Test
  public void givenErrorAsMinLevelWhenLogAllLevelsThenLogOnlyError() {

    lightLogger.setMinLogLevel(LogLevel.ERROR);

    restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    String consoleLogs = consoleOutput.toString();
    assertThat(consoleLogs).contains("[Console] ERROR: hello error");
    assertThat(consoleLogs).doesNotContain("[Console] INFO: hello info");
    assertThat(consoleLogs).doesNotContain("[Console] DEBUG: hello debug");
    assertThat(consoleLogs).doesNotContain("[Console] WARNING: hello warning");

  }

  @Test
  public void givenDebugAsMinLevelWhenLogAllLevelsThenLogAllLevel() {

    lightLogger.setMinLogLevel(LogLevel.DEBUG);


    restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    String consoleLogs = consoleOutput.toString();
    assertThat(consoleLogs).contains("[Console] ERROR: hello error");
    assertThat(consoleLogs).contains("[Console] INFO: hello info");
    assertThat(consoleLogs).contains("[Console] DEBUG: hello debug");
    assertThat(consoleLogs).contains("[Console] WARNING: hello warning");

  }

  @Test
  public void givenDebugAsMinLevelWithEmailWhenLogAllLevelsThenLogConsoleAndEmailAll() {

    lightLogger.setMinLogLevel(LogLevel.DEBUG);
    lightLogger.addLogTarget(new EmailLogTarget());


    // Make a request to the controller endpoint
    restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    // Assert that the logs are written to the console based on the configured log level
    String consoleLogs = consoleOutput.toString();
    assertThat(consoleLogs).contains("[Console] ERROR: hello error");
    assertThat(consoleLogs).contains("[Console] INFO: hello info");
    assertThat(consoleLogs).contains("[Console] DEBUG: hello debug");
    assertThat(consoleLogs).contains("[Console] WARNING: hello warning");

    assertThat(consoleLogs).contains("[Email] ERROR: hello error");
    assertThat(consoleLogs).contains("[Email] INFO: hello info");
    assertThat(consoleLogs).contains("[Email] DEBUG: hello debug");
    assertThat(consoleLogs).contains("[Email] WARNING: hello warning");

  }

  @Test
  public void givenWarningAsMinLevelWithWhenEmailErrorsOnlyThenLogConsoleAllAndSendEmail() {

    lightLogger.setMinLogLevel(LogLevel.WARNING);
    lightLogger.setLogLevelForTarget(new EmailLogTarget(),LogLevel.ERROR);

    restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    // Assert that the logs are written to the console based on the configured log level
    String consoleLogs = consoleOutput.toString();
    assertThat(consoleLogs).contains("[Console] ERROR: hello error");
    assertThat(consoleLogs).contains("[Console] WARNING: hello warning");
    assertThat(consoleLogs).contains("[Email] ERROR: hello error");

    assertThat(consoleLogs).doesNotContain("[Email] INFO: hello info");
    assertThat(consoleLogs).doesNotContain("[Email] DEBUG: hello debug");

  }
}
