# Log Demo 

Write a simple Java Spring Boot Application that uses the logger library.
Basic features
First, we want to implement functionality to log a string to the console.
Then, we want to add support for different log levels: Debug, Info, Warning, and Error.
While clients of the library can send any of these log levels, we should be able to configure which severity level is accepted at the moment.
The log levels order is Debug, Info, Warning, Error.
For example, if we configure that the minimum accepted log level is Warning, we will not handle Debug and Info logs. This is configured in runtime.
Extended features
Now, we want to add log targets. How can we change and best model our Logger library to send all received logs to different targets?
Our first target was a console, but now we want to e-mail, file system, server APIs, etc. Do not code the actual implementation of these targets (just log something that can be differentiated in the console). This is more of a model exercise.
Configuration features
Finally, how can we configure our minimum log levels per target?
For example, errors only go to e-mail but the console prints everything, etc.
It also needs to be configurable in runtime.


## How to test

Make sure logger library is installed under folder 
˜/.m2/repository/mylog/example/logger/1.0-SNAPSHOT/logger-1.0-SNAPSHOT.pom


run mvn clean test

## How to configure 

mylogger:

minLogLevel: WARNING

logTargets:
- type: console

- type: email

level: ERROR


You can update logger in runtime like above , please update application.yml
if you want to update log level and log targets 


## How to run 

mvn spring-boot:run

check the url http://localhost:8080/

Check the console logs 
























