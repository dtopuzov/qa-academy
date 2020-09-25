# Appium Demos

## About

Sample mobile tests against native Android and iOS application.

Technologies:
- [Maven](https://maven.apache.org/) as build system
- [Junit](https://junit.org/junit4/) as unit testing framework
- [Appium](http://appium.io/) to interact with mobile devices/emulators/simulators

Application under test:
- [WebdriverIO Demo App for iOS and Android](https://github.com/webdriverio/native-demo-app/releases)

## Execute Tests

Run all tests in single class:
```
mvn -Dtest=tests.SmokeTests test
```
    
Run all tests in package:
```
mvn -Dtest=tests.* test
```
